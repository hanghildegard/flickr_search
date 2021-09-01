package com.tori.flickrsearch.presentation.photolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.tori.flickrsearch.databinding.PhotolistFragmentBinding
import java.lang.IllegalStateException

import org.koin.android.ext.android.get

class PhotoListFragment : MvpFragment<PhotoListView, PhotoListPresenter>(), PhotoListView {

    private var _binding: PhotolistFragmentBinding? = null
    internal val binding: PhotolistFragmentBinding
        get() = _binding ?: throw IllegalStateException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PhotolistFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Test call with hard-coded search text for stage 1
        presenter.fetchPhotos("people")
    }

    override fun createPresenter(): PhotoListPresenter {
        return PhotoListPresenter(get())
    }

    override fun showResponse(response: String) {
        binding.responseTextview.text = response
    }
}