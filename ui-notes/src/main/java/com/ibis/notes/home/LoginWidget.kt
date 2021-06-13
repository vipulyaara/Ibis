package com.ibis.notes.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kafka.ui_common.alignCenter

@Composable
fun LoginWidget(modifier: Modifier = Modifier, onLoginClicked: () -> Unit) {
    val starColor = MaterialTheme.colors.background
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clickable { onLoginClicked() }
            .drawStars(
                remember { StarState(20, starColor) },
                clip = RoundedCornerShape(8.dp),
                backgroundColor = MaterialTheme.colors.primary
            )
    ) {
        Column(
            modifier = modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.subtitle2.alignCenter().copy(lineHeight = 20.sp),
                text = "You are using the demo account. Login to have your private notes.",
                color = MaterialTheme.colors.onPrimary
            )

            Button(
                modifier = Modifier
                    .padding(top = 24.dp),
                shape = RoundedCornerShape(24.dp),
                onClick = onLoginClicked,
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 12.dp,
                    pressedElevation = 4.dp
                ),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
            ) {
                Text(
                    style = MaterialTheme.typography.caption.alignCenter().copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    text = "Login to Ibis"
                )
            }
        }
    }
}
