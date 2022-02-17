package com.example.githubusers.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentFavouriteGithubUsersBinding
import com.example.githubusers.ui.activities.MainActivity
import com.example.githubusers.ui.adapters.RxJavaFavouriteGithubUsersAdapter
import com.example.githubusers.viewmodels.RxJavaFavouriteGithubUsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Fragment showing a list of favourite github users
 */
@AndroidEntryPoint
class FavouriteGithubUsersFragment : Fragment() {
    private val mDisposable = CompositeDisposable()

    private lateinit var _binding: FragmentFavouriteGithubUsersBinding
    private val binding get() = _binding

    @Inject
    lateinit var mViewModel: RxJavaFavouriteGithubUsersViewModel
    @Inject
    lateinit var mAdapter: RxJavaFavouriteGithubUsersAdapter

    /**
     * Documentation provided by Android
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteGithubUsersBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * Documentation provided by Android
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = getString(R.string.favourites)
        setHasOptionsMenu(true)

        mAdapter.setOnClickListener {
            val action = FavouriteGithubUsersFragmentDirections.actionFavouriteGithubUsersFragment2ToUserDetailFragment(it.id, it.userName)
            findNavController().navigate(action)
        }

        binding.githubUsersList.adapter = mAdapter

        mDisposable.add(mViewModel.getFavouriteGithubUsers().subscribe {
            lifecycleScope.launch {
                binding.progressBar.isVisible = false
                if (it.isEmpty()) {
                    binding.githubUsersList.isVisible = false
                    binding.emptyList.isVisible = true
                } else {
                    mAdapter.submitList(it)
                    binding.githubUsersList.isVisible = true
                    binding.emptyList.isVisible = false
                }
            }
        })
    }

    /**
     * Documentation provided by Android
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favourites_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Documentation provided by Android
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.delete_favourites) {
            mViewModel.deleteAllGithubUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Documentation provided by Android
     */
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.favouriteGithubUsersFragment2).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    /**
     * Documentation provided by Android
     */
    override fun onDestroy() {
        super.onDestroy()
        mDisposable.clear()
    }
}