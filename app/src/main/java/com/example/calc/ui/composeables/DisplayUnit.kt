package com.example.calc.ui.composeables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.calc.ui.theme.textColor

@Composable
fun DisplayUnit(
    modifier: Modifier = Modifier,
    arguments: State<String?>
) {
    val cardShape = RoundedCornerShape(16.dp)
    Card(
        shape = cardShape,
        border = BorderStroke(3.dp, textColor),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = arguments.value!!,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }
    }
}