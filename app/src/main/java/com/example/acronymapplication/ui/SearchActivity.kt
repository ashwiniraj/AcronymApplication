package com.example.acronymapplication.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.acronymapplication.R
import com.example.acronymapplication.databinding.ActivitySearchBinding

/**
 * Entry point for the application which provides UI to enter acronym and displays results in the list.
 */
class SearchActivity : AppCompatActivity() {
    private var adapter: SearchAcronymAdapter? = null
    private val viewModel: SearchViewModel by viewModels {
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SearchViewModel() as T
            }
        }
    }

    private val activitySearchBinding by lazy {
        DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = SearchAcronymAdapter()
        activitySearchBinding.apply {
            lifecycleOwner = this@SearchActivity
            searchVM = viewModel
            acronymMeaningsRecyclerview.layoutManager = LinearLayoutManager(this@SearchActivity)
            acronymMeaningsRecyclerview.adapter = adapter
            visible = false
        }

        registerObservers()
    }

    /**
     * Method to register the live data observers
     */
    private fun registerObservers() {
        viewModel.meaningsLiveData.observe(this) {
            it?.let {
                adapter?.submitList(it)
            }
        }

        viewModel.errorMessageLiveData.observe(this) {
            adapter?.submitList(listOf())
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        viewModel.stateLiveData.observe(this) {
            activitySearchBinding.visible = it
        }
    }
}
