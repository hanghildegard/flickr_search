package com.tori.flickrsearch.presentation.photolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.tori.flickrsearch.databinding.PhotolistFragmentBinding
import com.tori.flickrsearch.presentation.adapter.PhotoAdapterItem
import com.tori.flickrsearch.presentation.adapter.PhotoListAdapter
import java.lang.IllegalStateException

import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class PhotoListFragment : MvpFragment<PhotoListView, PhotoListPresenter>(), PhotoListView {

    private var _binding: PhotolistFragmentBinding? = null
    internal val binding: PhotolistFragmentBinding
        get() = _binding ?: throw IllegalStateException()

    private lateinit var photoListAdapter: PhotoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoListAdapter = PhotoListAdapter(ArrayList(), inject())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PhotolistFragmentBinding.inflate(inflater, container, false)

        val lm = LinearLayoutManager(requireContext())
        lm.orientation = RecyclerView.VERTICAL

        with(binding.photolistRecyclerview) {
            layoutManager = lm
            adapter = photoListAdapter
        }

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

    override fun clearItems() {
        photoListAdapter.clear()
        photoListAdapter.notifyDataSetChanged()
    }

    override fun showItems(items: List<PhotoAdapterItem>) {
        photoListAdapter.setItems(items)
        photoListAdapter.notifyDataSetChanged()
    }
}