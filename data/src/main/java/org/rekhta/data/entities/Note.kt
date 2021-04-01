package org.rekhta.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "note")
data class Note(
    @PrimaryKey @ColumnInfo(name = "note_id") var id: String = "",
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "text") var text: String = "",
    @ColumnInfo(name = "last_updated") var lastUpdated: Long = 0
) : RekhtaEntity {

    val formattedDate: String
        get() = SimpleDateFormat("MMM dd", Locale.ENGLISH).format(Date(lastUpdated))
}
