package org.rekhta.data.analytics

import javax.inject.Inject

class ContentEventRepository @Inject constructor(

) {
    fun searchPerformed(keyword: String): Map<String, String> {
        return mapOf("keyword" to keyword)
    }

    fun noteDetail(noteId: String, source: String): Map<String, String> {
        return mapOf(
            "note_id" to noteId,
            "source" to source,
            "note_title" to "",
        )
    }

    fun languageSelected(languageCode: Int, source: String) = mapOf(
        "language_code" to languageCode.toString(),
        "source" to source
    )
}
