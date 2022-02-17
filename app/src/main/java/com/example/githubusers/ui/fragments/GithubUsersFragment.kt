package com.example.githubusers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.databinding.FragmentGithubUsersBinding
import com.example.githubusers.ui.activities.MainActivity
import com.example.githubusers.ui.adapters.GithubUsersLoadStateAdapter
import com.example.githubusers.ui.adapters.RxJavaGithubUsersAdapter
import com.example.githubusers.utility.Constants
import com.example.githubusers.viewmodels.RxJavaGithubUsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Fragment showing a paged list of all github users that match the "lagos" search term
 */
@AndroidEntryPoint
class GithubUsersFragment : Fragment() {
    private val mDisposable = CompositeDisposable()

    private lateinit var _binding: FragmentGithubUsersBinding
    private val binding get() = _binding

    @Inject lateinit var mViewModel: RxJavaGithubUsersViewModel
    @Inject lateinit var mAdapter: RxJavaGithubUsersAdapter

    /**
     * Documentation provided by Android
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGithubUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Documentation provided by Android
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "${Constants.GITHUB_QUERY_TERM} Github Users"

        // map retry button to paged adapter's reply function
        binding.retryButton.setOnClickListener { mAdapter.retry() }

        // set footer loader for paged adapter
        binding.githubUsersList.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        binding.githubUsersList.adapter = mAdapter.withLoadStateHeaderAndFooter(
            header = GithubUsersLoadStateAdapter { mAdapter.retry() },
            footer = GithubUsersLoadStateAdapter { mAdapter.retry() }
        )

        mAdapter.setOnClickListener {
            val action = GithubUsersFragmentDirections.actionGithubUsersFragmentToUserDetailFragment(it.id, it.userName)
            findNavController().navigate(action)
        }

        mAdapter.addLoadStateListener {
            if (it.source.refresh is LoadState.NotLoading) {
                binding.progressBar.isVisible = false
                if (it.append.endOfPaginationReached && mAdapter.itemCount < 1) {
                    binding.githubUsersList.isVisible = false
                    binding.emptyList.isVisible = true
                    binding.retryButton.isVisible = true
                } else {
                    binding.githubUsersList.isVisible = true
                    binding.emptyList.isVisible = false
                    binding.retryButton.isVisible = false
                }
            }
        }

        // listen/subscribe to changes from RxJava flowable
        mDisposable.add(mViewModel.getGithubUsers().subscribe {
            lifecycleScope.launch {
                mAdapter.submitData(it)
            }
        })
    }

    /**
     * Documentation provided by Android
     */
    override fun onDestroy() {
        super.onDestroy()
        mDisposable.clear()
    }
}