package com.tawajood.vetclinic.ui.main.reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.adapters.NotificationAdapter
import com.tawajood.vetclinic.adapters.ReviewsAdapter
import com.tawajood.vetclinic.databinding.FragmentNotificationsBinding
import com.tawajood.vetclinic.databinding.FragmentReviewsBinding
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.ui.main.notifications.NotificationsViewModel
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ReviewsFragment : Fragment(R.layout.fragment_reviews) {


    private lateinit var binding: FragmentReviewsBinding
    private lateinit var parent: MainActivity
    private val viewModel: ReviewsViewModel by viewModels()
    private lateinit var reviewsAdapter: ReviewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewsBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        observeData()
        setupReviews()

    }

    private fun setupReviews() {
        reviewsAdapter = ReviewsAdapter()

        binding.rvReviews.adapter = reviewsAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.reviews.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        reviewsAdapter.reviews = it.data!!.reviews.reviews
                    }
                }
            }
        }
    }

    private fun setupUI() {

        parent.setTitle(getString(R.string.reviews))
        parent.showBottomNav(false)

    }


}