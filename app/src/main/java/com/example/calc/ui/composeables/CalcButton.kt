package com.example.calc.ui.composeables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calc.models.ButtonModel
import com.example.calc.models.ButtonType
import com.example.calc.ui.theme.colorPrimary
import com.example.calc.ui.theme.colorPrimaryDark
import com.example.calc.ui.theme.colorPrimaryLight
import com.example.calc.ui.theme.textColor
import kotlinx.coroutines.delay

enum class ButtonSkin(val color: Color) {
    Primary(colorPrimary),
    PrimaryLight(colorPrimaryLight),
    PrimaryDark(colorPrimaryDark)
}

@Composable
fun CalcButton(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.headlineLarge.fontSize,
    button: ButtonModel
) {
    var isButtonClicked by remember { mutableStateOf(false) }
    LaunchedEffect(isButtonClicked) {
        delay(150) // Adjust the delay duration as needed
        isButtonClicked = false
    }

    Surface(
        shape = CircleShape,
        color = button.type.skin.color,
        modifier = modifier
            .shadow(4.dp, shape = CircleShape)
            .alpha(if (isButtonClicked) 0.7f else 1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = if (button.type.skin == ButtonSkin.PrimaryLight)
                        Color.Black.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.2f)
                ) // Change the ripple color here
            ) {
                isButtonClicked = true
                button.onClick()
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = button.type.symbol,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSize,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
        }
    }
}