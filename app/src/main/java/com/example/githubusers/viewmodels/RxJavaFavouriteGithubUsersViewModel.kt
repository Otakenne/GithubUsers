package com.example.githubusers.viewmodels

import androidx.lifecycle.ViewModel
import com.example.githubusers.data.RxJavaFavoriteGithubUsersRepository
import com.example.githubusers.model.GithubUser
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * ViewModel for the FavouriteGithubUsersFragment
 */
class RxJavaFavouriteGithubUsersViewModel @Inject constructor(
    private val githubUserRepository: RxJavaFavoriteGithubUsersRepository
): ViewModel() {
    private var myCompositeDisposable = CompositeDisposable()

    /**
     * Deletes all github users from the room database and makes a call to getFavouriteGithubUsers() for the calling fragment to resubscribe
     * @author Otakenne
     */
    fun deleteAllGithubUsers() {
        githubUserRepository.deleteAllGithubUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                getFavouriteGithubUsers()
            },{}).let { }
    }

    /**
     * Gets all the persisted github users from the github_users_table in the room database
     * @return a flowable the calling fragment can subscribe to
     * @author Otakenne
     */
    fun getFavouriteGithubUsers(): Flowable<List<GithubUser>> {
        return githubUserRepository.getAllGithubUsers()
    }

    /**
     * Documentation provided by Android
     */
    override fun onCleared() {
        super.onCleared()
        myCompositeDisposable.clear()
    }
}