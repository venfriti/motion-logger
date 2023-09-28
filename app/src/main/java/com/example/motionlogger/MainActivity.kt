package com.example.motionlogger


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.motionlogger.ui.components.TopCard
import com.example.motionlogger.ui.theme.DarkBlue900
import com.example.motionlogger.ui.theme.MotionLoggerTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MotionLoggerTheme {
                Surface(
                    color = DarkBlue900
                ) {
                    AppScreen()
                }

            }
        }
    }
}

@Composable
fun AppScreen(){
    Column {
        HeaderTab()
        TopCard()
    }
}

@Composable
fun HeaderTab(modifier: Modifier = Modifier) {
    MotionLoggerTheme{
        Row(
            modifier = modifier
                .padding(16.dp)
                .animateContentSize()
                .height(TabHeight)
                .fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Filled.PieChart,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.width(12.dp))
            Text("MotionLogger",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}




//@Composable
//fun FullDialog(){
//    var isSending by remember { mutableStateOf(false) }
//
//    if (isSending){
//        RallyAlertDialog(
//            onDismiss = {
//                isSending = false
//            },
//            bodyText = "alertMessage",
//            buttonText = "Dismiss".uppercase(Locale.getDefault())
//        )
//    }
//
//    Column {
//        InputField {
//            isSending = true
//        }
//        RallyDivider(
//            modifier = Modifier.padding(start = mediumPadding, end = mediumPadding)
//        )
//    }
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RallyAlertDialog(
//    onDismiss: () -> Unit,
//    bodyText: String,
//    buttonText: String
//) {
//    MaterialTheme {
//        AlertDialog(
//            onDismissRequest = onDismiss,
//            modifier = Modifier,
//            prop
//        )
//    }
//}
//
//@Composable
//fun content(){
//    text = { Text(bodyText) },
//    buttons = {
//        Column {
//            Divider(
//                Modifier.padding(horizontal = 12.dp),
//                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
//            )
//            TextButton(
//                onClick = onDismiss,
//                shape = RectangleShape,
//                contentPadding = PaddingValues(mediumPadding),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(buttonText)
//            }
//        }
//    }
//}

@Composable
fun InputField(onClickStart: () -> Unit){
    Row(
        modifier = Modifier
            .padding(mediumPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Alerts",
//            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        TextButton(
            onClick = onClickStart,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "START",
//                style = MaterialTheme.typography.button
            )
        }
    }
}



@Preview
@Composable
fun HeaderTabPreview() {
    MaterialTheme { HeaderTab(Modifier.padding(8.dp)) }
}

private val mediumPadding = 16.dp

private val TabHeight = 56.dp