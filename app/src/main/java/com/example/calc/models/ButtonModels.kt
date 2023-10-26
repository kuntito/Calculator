package com.example.calc.models

import com.example.calc.ui.composeables.ButtonSkin

sealed class CalcOperator(val symbol: String) {
    object Add: CalcOperator("+")
    object Subtract: CalcOperator("-")
    object Multiply: CalcOperator("x")
    object Divide: CalcOperator("/")

    companion object {
        fun isOperator(str: String?): Boolean = str in listOf(
            Add.symbol,
            Subtract.symbol,
            Multiply.symbol,
            Divide.symbol
        )
    }
}

sealed class MiscButton(val symbol: String) {
    object Clear: MiscButton("c")
    object LeftParen: MiscButton("(")
    object RightParen: MiscButton(")")
    object Delete: MiscButton("del")
    object Decimal: MiscButton(".")
}

sealed class ButtonType(
    val symbol: String,
    val skin: ButtonSkin
) {
    data class Operation(
        val op: CalcOperator
    ): ButtonType(op.symbol, skin = ButtonSkin.PrimaryDark)

    data class Digit(
        val number: Int
    ): ButtonType("$number", skin = ButtonSkin.Primary)

    data class Misc (
        val miscButton: MiscButton
    ): ButtonType(miscButton.symbol, skin = ButtonSkin.PrimaryLight)

    object Equals: ButtonType("=", skin = ButtonSkin.PrimaryDark)
}

data class ButtonModel(
    val type: ButtonType,
    private val _onClick: (ButtonType) -> Unit
) {
    val onClick = { _onClick(type) }
}