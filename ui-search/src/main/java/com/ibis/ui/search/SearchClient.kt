package com.ibis.ui.search

import com.algolia.search.client.ClientSearch
import com.algolia.search.dsl.attributesToRetrieve
import com.algolia.search.dsl.query
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.Attribute
import com.algolia.search.model.search.Query
import com.algolia.search.model.search.SortFacetsBy
import javax.inject.Inject

class SearchClient @Inject constructor() {
    init {
        val appID = ApplicationID("P3U5AXJEGG")
        val apiKey = APIKey("7ad94b92a90f973a11a2c6e61d03ffb9")

        val client = ClientSearch(appID, apiKey)

        val query = query {
            attributesToRetrieve {
                +"color"
                +"category"
            }
        }
    }

    fun findAttribute() {
        val color = Attribute("color")
        val category = Attribute("category")
        val query = Query(attributesToRetrieve = listOf(color, category))
        query.sortFacetsBy = SortFacetsBy.Count
    }
}
