package com.carce.countrysearch.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.viewModels
import com.carce.countrysearch.R
import com.carce.countrysearch.view.components.CountrySearch
import com.carce.countrysearch.viewmodel.CountryViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SearchFragment : Fragment() {

    private val viewModel: CountryViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_search, container, false)
        viewModel.getCountries()

        rootView.findViewById<ComposeView>(R.id.search_compose_view).apply {
            setContent {
                ConstraintLayout {
                    val (title, search) = createRefs()
                    CountrySearch(getString(R.string.search_results_title), Modifier, viewModel)
                }
            }
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}