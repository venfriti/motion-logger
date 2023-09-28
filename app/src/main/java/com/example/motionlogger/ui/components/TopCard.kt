package com.example.motionlogger.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopCard(modifier: Modifier = Modifier) {
    var isSending by remember { mutableStateOf(false) }

    var currentTargetElevation by remember { mutableStateOf(1.dp) }
    LaunchedEffect(Unit) {
        // Start the animation
        currentTargetElevation = 8.dp
    }
    val animatedElevation = animateDpAsState(
        targetValue = currentTargetElevation,
        animationSpec = tween(durationMillis = 500),
        finishedListener = {
            currentTargetElevation = if (currentTargetElevation > 4.dp) {
                1.dp
            } else {
                8.dp
            }
        },
        label = "Card Animation"
    )

    Card(elevation = CardDefaults.cardElevation(defaultElevation = animatedElevation.value)
    ) {
        Column {
            AlertHeader {
                isSending = !isSending
            }
            RallyDivider()
            TextDialog()
        }
    }


}

@Composable
fun AlertHeader(onClickSend: () -> Unit){
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = "Enter Url",
            onValueChange = {},
            textStyle = TextStyle(MaterialTheme.colorScheme.onSurface)
        )
        TextButton(
            onClick = onClickSend,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "SEE ALL",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun RallyDivider(modifier: Modifier = Modifier) {
    Divider(color = MaterialTheme.colorScheme.background, thickness = 1.dp, modifier = modifier)
}


@Composable
fun TextDialog(modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Response",
            color = MaterialTheme.colorScheme.background,
            maxLines = 3
        )
    }
}

@Preview
@Composable
fun InputDialogPreview(){
    AlertHeader{}
}
