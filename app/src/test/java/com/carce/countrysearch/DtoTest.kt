package com.carce.countrysearch

import com.carce.countrysearch.model.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Test

class DtoTest {

    private val gson = Gson()

    @Test
    fun `GIVEN a json WHEN it is a valid country THEN it is parsed correctly`() {
        val collectionType = object : TypeToken<List<Country>>() {}.type
        val countries = gson.fromJson<List<Country>>(JsonHolder.countryExampleJson, collectionType)
        assert(countries.size == 1)
        assert(countries[0].flags?.png == "https://flagcdn.com/w320/de.png")
        assert(countries[0].name?.common == "Germany")
        assert(countries[0].name?.official == "Federal Republic of Germany")
        assert(countries[0].capital?.get(0) == "Berlin")
        assert(countries[0].region == "Europe")
        assert(countries[0].subregion == "Western Europe")
        assert(countries[0].languages?.get("deu") == "German")
        assert(countries[0].currencies?.get("EUR")?.name == "Euro")
        assert(countries[0].currencies?.get("EUR")?.symbol == "€")
        assert(countries[0].population == 83240525)
        assert(countries[0].countryCarInfo?.drivingSide == "right")
        assert(countries[0].coatOfArms?.png == "https://mainfacts.com/media/images/coats_of_arms/de.png")
    }
}
