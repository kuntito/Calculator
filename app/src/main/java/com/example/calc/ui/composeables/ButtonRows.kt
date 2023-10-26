package com.example.calc.ui.composeables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calc.CalcViewModel
import com.example.calc.models.ButtonModel

@Composable
fun ButtonRow(
    modifier: Modifier = Modifier,
    buttons: List<ButtonModel>,
    buttonsPerRow: Int = 4
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (i in 0 until buttonsPerRow) {
            CalcButton(
                button = buttons[i],
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
        }
    }
}

@Composable
fun ButtonRows(
    modifier: Modifier = Modifier,
    viewModel: CalcViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ButtonRow(buttons = listOf(
            viewModel.btnClear,
            viewModel.btnLeftParen,
            viewModel.btnRightParen,
            viewModel.btnDivide
        ))
        ButtonRow(buttons = listOf(
            viewModel.btnSeven,
            viewModel.btnEight,
            viewModel.btnNine,
            viewModel.btnMultiply
        ))
        ButtonRow(buttons = listOf(
            viewModel.btnFour,
            viewModel.btnFive,
            viewModel.btnSix,
            viewModel.btnSubtract
        ))
        ButtonRow(buttons = listOf(
            viewModel.btnOne,
            viewModel.btnTwo,
            viewModel.btnThree,
            viewModel.btnAdd
        ))
        ButtonRow(buttons = listOf(
            viewModel.btnDelete,
            viewModel.btnZero,
            viewModel.btnDecimal,
            viewModel.btnEquals
        ))
    }
}