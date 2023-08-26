package com.example.motionlogger


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.motionlogger.ui.theme.Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            HeaderTab()
        }
    }
}

@Composable
fun HeaderTab(modifier: Modifier = Modifier) {
    Theme{
        Row(
            modifier = modifier
                .padding(16.dp)
                .animateContentSize()
                .height(TabHeight)
                .fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Filled.PieChart,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface)
            Spacer(Modifier.width(12.dp))
            Text("MotionLogger",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h5)
        }
    }
}

@Composable
fun InputDialog(modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = "Enter Url",
            onValueChange = {},
            textStyle = TextStyle(MaterialTheme.colors.onSurface)
        )
        val onClickSeeAll = {}
        TextButton(
            onClick = onClickSeeAll,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "SEE ALL",
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Preview
@Composable
fun InputDialogPreview(){
    InputDialog()
}

//@Composable
//private fun AlertHeader(onClickSeeAll: () -> Unit) {
//    Row(
//        modifier = Modifier
//            .padding(RallyDefaultPadding)
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            text = "Alerts",
//            style = MaterialTheme.typography.subtitle2,
//            modifier = Modifier.align(Alignment.CenterVertically)
//        )
//        TextButton(
//            onClick = onClickSeeAll,
//            contentPadding = PaddingValues(0.dp),
//            modifier = Modifier.align(Alignment.CenterVertically)
//        ) {
//            Text(
//                text = "SEE ALL",
//                style = MaterialTheme.typography.button
//            )
//        }
//    }
//}

@Preview
@Composable
fun HeaderTabPreview() {
    Theme { HeaderTab(Modifier.padding(8.dp)) }
}

private val TabHeight = 56.dp