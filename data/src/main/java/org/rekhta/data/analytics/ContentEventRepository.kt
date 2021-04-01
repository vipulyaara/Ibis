package org.rekhta.data.analytics

import javax.inject.Inject

class ContentEventRepository @Inject constructor(

) {
    fun searchPerformed(keyword: String): Map<String, String> {
        return mapOf("keyword" to keyword)
    }

    fun poetDetail(poetId: String, source: String): Map<String, String> {
        return mapOf(
            "poet_id" to poetId,
            "source" to source,
            "poet_name" to "",
        )
    }

    fun contentDetail(contentId: String, contentTitle: String) = mapOf(
        "content_id" to contentId,
        "content_title" to contentTitle
    )

    fun languageSelected(languageCode: Int, source: String) = mapOf(
        "language_code" to languageCode.toString(),
        "source" to source
    )
}
