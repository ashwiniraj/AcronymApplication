package com.example.acronymapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.acronymapplication.databinding.MeaningListItemBinding
import com.example.acronymapplication.datamodels.AcronymMeaningsListData

/**
 * SearchAcronymAdapter class is responsible to bind the data to recyclerview
 */
class SearchAcronymAdapter :
    ListAdapter<AcronymMeaningsListData, SearchAcronymAdapter.SearchAcronymViewHolder>(
        SearchAcronymDiffCallback()
    ) {

    lateinit var binding: MeaningListItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAcronymViewHolder {
        binding = MeaningListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchAcronymViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchAcronymViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SearchAcronymDiffCallback : DiffUtil.ItemCallback<AcronymMeaningsListData>() {
        override fun areItemsTheSame(
            oldItem: AcronymMeaningsListData,
            newItem: AcronymMeaningsListData
        ): Boolean = oldItem.longForms == newItem.longForms

        override fun areContentsTheSame(
            oldItem: AcronymMeaningsListData,
            newItem: AcronymMeaningsListData
        ): Boolean = oldItem == newItem
    }

    inner class SearchAcronymViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: AcronymMeaningsListData) {
            binding.meaningText = item.longForms
        }
    }
}
