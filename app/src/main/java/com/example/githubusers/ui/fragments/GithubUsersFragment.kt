package com.example.githubusers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.api.GithubUsersService
import com.example.githubusers.data.RxJavaGetGithubUsersRepositoryImpl
import com.example.githubusers.data.RxJavaGithubUsersPagingSource
import com.example.githubusers.databinding.FragmentGithubUsersBinding
import com.example.githubusers.ui.activities.MainActivity
import com.example.githubusers.ui.adapters.GithubUsersLoadStateAdapter
import com.example.githubusers.ui.adapters.RxJavaGithubUsersAdapter
import com.example.githubusers.utility.Constants
import com.example.githubusers.viewmodels.RxJavaGithubUsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GithubUsersFragment : Fragment() {
    private val mDisposable = CompositeDisposable()

    private lateinit var _binding: FragmentGithubUsersBinding
    private val binding get() = _binding

    @Inject lateinit var mViewModel: RxJavaGithubUsersViewModel
    @Inject lateinit var mAdapter: RxJavaGithubUsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGithubUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "${Constants.GITHUB_QUERY_TERM} Github Users"

        binding.retryButton.setOnClickListener { mAdapter.retry() }

        binding.githubUsersList.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        binding.githubUsersList.adapter = mAdapter.withLoadStateHeaderAndFooter(
            header = GithubUsersLoadStateAdapter { mAdapter.retry() },
            footer = GithubUsersLoadStateAdapter { mAdapter.retry() }
        )

        mAdapter.setOnClickListener {
            Toast.makeText(view.context, "${it.userName} Clicked", Toast.LENGTH_SHORT).show()
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

        mDisposable.add(mViewModel.getGithubUsers().subscribe {
            lifecycleScope.launch {
                mAdapter.submitData(it)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.clear()
    }
}