package com.example.githubusers.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentUserDetailBinding
import com.example.githubusers.ui.activities.MainActivity
import com.example.githubusers.viewmodels.RxJavaGithubUserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment showing github user profile
 */
@AndroidEntryPoint
class UserDetailFragment : Fragment() {
    private val navigationArgs: UserDetailFragmentArgs by navArgs()
    private lateinit var _binding: FragmentUserDetailBinding
    private val binding get() = _binding

    private val mViewModel: RxJavaGithubUserViewModel by viewModels()

    /**
     * Documentation provided by Android
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * Documentation provided by Android
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "@${navigationArgs.githubUserUsername}"
        setHasOptionsMenu(true)

        binding.lifecycleOwner = this
        binding.viewModel = mViewModel
    }

    /**
     * Documentation provided by Android
     */
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.favouriteGithubUsersFragment2).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}