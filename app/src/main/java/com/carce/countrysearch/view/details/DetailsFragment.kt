package com.carce.countrysearch.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import com.carce.countrysearch.R
import com.carce.countrysearch.databinding.FragmentDetailBinding
import com.carce.countrysearch.model.Country
import com.carce.countrysearch.viewmodel.CountryViewModel

class DetailsFragment : Fragment() {

    private lateinit var viewModel: CountryViewModel
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get()

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        beginCollectingFromVM()

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.navigateBackToSearch()
            findNavController().navigate(R.id.action_DetailsFragment_to_SearchFragment)
        }

        return binding.root
    }

    private fun beginCollectingFromVM() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                if (it.selectedCountry != null) {
                    populateScreen(it.selectedCountry)
                }
            }
        }
    }

    private fun populateScreen(country: Country) {

        binding.apply {
            detailsTitle.text = country.name?.common

            detailsFlag.load(country.flags?.png)
            detailsCommonName.text = country.name?.common
            detailsOfficialName.text = country.name?.official
            detailsCoatImage.load(country.coatOfArms?.png)

            detailsListRv.layoutManager = LinearLayoutManager(context)
            detailsListRv.adapter = DetailsAdapter(country)

            backIcon.setOnClickListener {
                viewModel.navigateBackToSearch()
                findNavController().navigate(R.id.action_DetailsFragment_to_SearchFragment)
            }
            backText.setOnClickListener {
                viewModel.navigateBackToSearch()
                findNavController().navigate(R.id.action_DetailsFragment_to_SearchFragment)
            }

        }
    }
}
