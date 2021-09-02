package com.tori.flickrsearch.presentation.photolist

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.TextWatcherAdapter
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

        with(binding.searchInputField) {
            addTextChangedListener(object : TextWatcherAdapter() {
                override fun afterTextChanged(s: Editable) {
                    presenter.onSearchTextChanged(s.toString())
                }
            })

            setOnEditorActionListener { editText, actionsId, keyEvent ->
                if (actionsId == EditorInfo.IME_ACTION_SEARCH ||
                    (keyEvent?.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) {
                    presenter.fetchPhotos(editText.text.toString())

                    view?.let {
                        val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                    }
                }
                false
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(savedInstanceState?.getString(KEY_SEARCH_TEXT))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun createPresenter(): PhotoListPresenter {
        return PhotoListPresenter(get())
    }

    override fun showContent(show: Boolean) {
        binding.photolistRecyclerview.isVisible = show
    }

    override fun showEmpty(show: Boolean) {
        binding.photolistEmptyView.root.isVisible = show
    }

    override fun updateEmptyLabel(labelId: Int) {
        binding.photolistEmptyView.emptyLabel.text = getString(labelId)
    }

    override fun showLoading(show: Boolean) {
        binding.photolistLoadingView.isVisible = show
    }

    override fun showError(show: Boolean) {
        binding.photolistErrorView.root.isVisible = show
    }

    override fun clearItems() {
        photoListAdapter.clear()
        photoListAdapter.notifyDataSetChanged()
    }

    override fun showItems(items: List<PhotoAdapterItem>) {
        photoListAdapter.setItems(items)
        photoListAdapter.notifyDataSetChanged()
    }

    companion object {
        const val KEY_SEARCH_TEXT = "key_search_text"
    }
}