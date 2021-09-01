package com.tori.flickrsearch.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tori.flickrsearch.R

class PhotoListAdapter(private val dataSet: MutableList<PhotoAdapterItem>, private val picasso: Lazy<Picasso>) : RecyclerView.Adapter<PhotoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.photolist_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet.get(position)

        picasso.value.load(item.photoUrl)
            .into(holder.imageView)
        holder.titleTextView.text = item.title
        holder.ownerTextView.text = item.owner
    }

    override fun getItemCount(): Int = dataSet.size

    fun addItem(item: PhotoAdapterItem) {
        dataSet.add(item)
    }

    fun addItems(items: List<PhotoAdapterItem>) {
        dataSet.addAll(items)
    }

    fun setItems(items: List<PhotoAdapterItem>) {
        dataSet.clear()
        dataSet.addAll(items)
    }

    fun clear() {
        dataSet.clear()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        val titleTextView: TextView
        val ownerTextView: TextView

        init {
            imageView = view.findViewById(R.id.listitem_imageview)
            titleTextView = view.findViewById(R.id.listitem_title_textview)
            ownerTextView = view.findViewById(R.id.listitem_owner_textview)
        }
    }
}