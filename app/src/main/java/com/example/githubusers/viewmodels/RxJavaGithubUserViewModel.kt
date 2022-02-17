package com.example.githubusers.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.RxJavaGithubUserRepository
import com.example.githubusers.model.GithubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RxJavaGithubUserViewModel @Inject constructor(
    private val githubUserRepository: RxJavaGithubUserRepository,
    private val state: SavedStateHandle
): ViewModel() {

    private var myCompositeDisposable = CompositeDisposable()
    val loading = ObservableBoolean(false)
    val githubUser = ObservableField<GithubUser>()
    val isError = ObservableBoolean(false)

    private val username = state.get<String>("github_user_username")!!
    private val uuid = state.get<Long>("github_user_id")!!

    init {
        githubUserExists()
    }

    fun insertOrDeleteGithubUserFromRoom() {
        when (githubUser.get()?.isFavourite) {
            true -> deleteGithubUserFromRoom()
            else -> insertGithubUserToRoom()
        }
    }

    fun insertGithubUserToRoom() {
        githubUser.get()?.let {
            it.isFavourite = true
            githubUserRepository.insertGithubUserToRoom(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    getGithubUserFromRoom(uuid)
                },{}).let { }
        }
    }

    fun deleteGithubUserFromRoom() {
        githubUser.get()?.let {
            githubUserRepository.deleteGithubUserFromRoom(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    getGithubUserFromAPI(username)
                },{}).let { }
        }
    }

    fun githubUserExists() {
        val flowable = uuid.let { it ->
            githubUserRepository.githubUserExists(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { loading.set(true) }
                .doAfterTerminate { loading.set(false) }
                .subscribe ({
                    if (it) {
                        getGithubUserFromRoom(uuid)
                    } else {
                        getGithubUserFromAPI(username)
                    }

                }, { _: Throwable? ->
                    getGithubUserFromAPI(username)
                })
        }
        myCompositeDisposable.add(flowable)
    }

    fun getGithubUserFromRoom(UUID: Long) {
        val flowable = githubUserRepository.getGithubUserFromRoom(UUID)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { loading.set(true) }
            .doAfterTerminate { loading.set(false) }
            .subscribe ({
                this.githubUser.set(it)
                this.isError.set(false)
                this.loading.set(false)
            }, { _: Throwable? ->
                this.isError.set(true)
            })
        loading.set(false)
        myCompositeDisposable.add(flowable)
    }

    private fun getGithubUserFromAPI(username: String) {
        val flowable = githubUserRepository.getGithubUserFromAPI(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.set(true) }
            .doAfterTerminate { loading.set(false) }
            .subscribe({ t: GithubUser? ->
                t?.let {
                    this.githubUser.set(it)
                    this.isError.set(false)
                }
            }, { _: Throwable? ->
                this.isError.set(true)
            })

        loading.set(false)
        myCompositeDisposable.add(flowable)
    }

    override fun onCleared() {
        super.onCleared()
        myCompositeDisposable.clear()
    }
}