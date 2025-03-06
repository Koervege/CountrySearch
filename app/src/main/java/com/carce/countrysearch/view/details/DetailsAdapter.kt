package com.carce.countrysearch.view.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carce.countrysearch.R
import com.carce.countrysearch.databinding.DetailsListItemBinding
import com.carce.countrysearch.model.Country
import java.text.NumberFormat
import java.util.Locale

class DetailsAdapter(private val country: Country): RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    companion object {
        private const val CAPITAL = "Capital"
        private const val REGION = "Region"
        private const val SUBREGION = "Subregion"
        private const val LANGUAGES = "Languages"
        private const val CURRENCIES = "Currencies"
        private const val POPULATION = "Population"
        private const val CAR_SIDE = "Car Driver Side"
        private const val COAT_OF_ARMS = "Coat of Arms"

        val keyList = listOf(CAPITAL, REGION, SUBREGION, LANGUAGES, CURRENCIES, POPULATION, CAR_SIDE, COAT_OF_ARMS)
    }
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DetailsListItemBinding.bind(view)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.details_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val formattedKeyString = viewHolder.binding.root.context.getString(R.string.details_key, keyList[position])

        when(position) {
            0 -> {
                viewHolder.binding.detailKey.text = formattedKeyString
                viewHolder.binding.detailValue.text = country.capital.takeUnless { it.isNullOrEmpty() }?.get(0) ?: "No Capital"
            }
            1 -> {
                viewHolder.binding.detailKey.text = formattedKeyString
                viewHolder.binding.detailValue.text = country.region ?: "None"
            }
            2 -> {
                viewHolder.binding.detailKey.text = formattedKeyString
                viewHolder.binding.detailValue.text = country.subregion ?: "None"
            }
            3 -> {
                viewHolder.binding.detailKey.text = formattedKeyString
                viewHolder.binding.detailValue.text =
                    country.languages?.values?.joinToString(", ").takeUnless { it.isNullOrBlank() } ?: "None"
            }
            4 -> {
                viewHolder.binding.detailKey.text = formattedKeyString
                viewHolder.binding.detailValue.text = country.currencies?.keys?.joinToString(", ") {
                    "$it (${country.currencies[it]?.name})"
                }.takeUnless { it.isNullOrBlank() } ?: "None"
            }
            5 -> {
                viewHolder.binding.detailKey.text = formattedKeyString
                viewHolder.binding.detailValue.text = NumberFormat.getNumberInstance(Locale.US).format(country.population)
            }
            6 -> {
                viewHolder.binding.detailKey.text = formattedKeyString
                viewHolder.binding.detailValue.text = country.countryCarInfo?.drivingSide?.replaceFirstChar { it: Char ->
                    it.uppercase()[0]
                } ?: "None"
            }
            7 -> {
                viewHolder.binding.detailKey.text = formattedKeyString.dropLast(2)
            }
            else -> {}
        }
    }

    override fun getItemCount() = 8
}
