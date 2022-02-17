package com.example.githubusers.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava2.cachedIn
import com.example.githubusers.data.RxJavaFavoriteGithubUsersRepository
import com.example.githubusers.data.RxJavaGithubUserRepository
import com.example.githubusers.model.GithubUser
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RxJavaFavouriteGithubUsersViewModel @Inject constructor(
    private val githubUserRepository: RxJavaFavoriteGithubUsersRepository
): ViewModel() {
    private var myCompositeDisposable = CompositeDisposable()

    fun deleteAllGithubUsers() {
        githubUserRepository.deleteAllGithubUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                getFavouriteGithubUsers()
            },{}).let { }
    }

    fun getFavouriteGithubUsers(): Flowable<List<GithubUser>> {
        return githubUserRepository.getAllGithubUsers()
    }

    override fun onCleared() {
        super.onCleared()
        myCompositeDisposable.clear()
    }
}