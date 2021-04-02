package com.kafka.ui_common.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kafka.ui_common.R
import com.kafka.ui_common.alignCenter

@ExperimentalAnimationApi
@Composable
fun FullScreenMessage(
    title: String,
    message: String = "",
    show: Boolean = true
) {
    AnimatedVisibility(visible = show) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
                .padding(vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            Image(
                painter = painterResource(id = R.drawable.img_absurd_bulb),
                contentDescription = null,
                modifier = Modifier
                    .weight(0.4f)
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = title,
                style = MaterialTheme.typography.h6.alignCenter()
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.body2.alignCenter()
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}
