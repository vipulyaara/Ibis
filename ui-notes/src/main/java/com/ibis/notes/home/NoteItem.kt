package com.ibis.notes.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kafka.ui_common.theme.textPrimary
import com.kafka.ui_common.theme.textSecondary
import org.rekhta.data.entities.Note

@ExperimentalAnimationApi
@Composable
fun NoteItem(
    note: Note,
    onContentClicked: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .clickable { onContentClicked(note.id) }
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.padding(4.dp))

                Text(
                    text = note.text,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.textSecondary,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.padding(4.dp))

                Text(
                    text = note.formattedDate,
                    style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colors.primary
                )
            }
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colors.textPrimary.copy(alpha = 0.05f)
        )
    }
}
