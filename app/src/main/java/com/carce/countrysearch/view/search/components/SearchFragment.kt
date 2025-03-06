package com.carce.countrysearch.view.search.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.carce.countrysearch.R
import com.carce.countrysearch.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var viewModel: CountryViewModel
    private lateinit var rootView: View

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        rootView = inflater.inflate(R.layout.fragment_search, container, false)
        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get()
        viewModel.getCountries()

        rootView.findViewById<ComposeView>(R.id.search_compose_view).apply {
            setContent {
                CountrySearch(getString(R.string.search_results_title), viewModel)
            }
        }
        beginCollectingFromVM()
        return rootView
    }

    private fun beginCollectingFromVM() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                if (it.selectedCountry != null) findNavController().navigate(R.id.action_SearchFragment_to_DetailFragment)
            }
        }
    }
}
