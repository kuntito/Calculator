package com.example.calc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calc.misc.isCharExistAfterLastOperator
import com.example.calc.misc.isUnmatchedLeftParenExist
import com.example.calc.models.ButtonModel
import com.example.calc.models.ButtonType
import com.example.calc.models.CalcArguments
import com.example.calc.models.CalcOperator
import com.example.calc.models.MiscButton

const val viewModelTag = "viewModelTag"
class CalcViewModel: ViewModel() {
    private var _showToast = MutableLiveData(false)
    val showToast: LiveData<Boolean>
        get() = _showToast

    fun setShowToast(flag: Boolean) {
        _showToast.value = flag
    }

    private var _toastMessage = ""
    val toastMessage: String
        get() = _toastMessage

    private fun setToastMessage(msg: String) {
        _toastMessage = msg
        setShowToast(true)
    }

    val arguments = CalcArguments()

    private val onDigitClick: (ButtonType) -> Unit = {
        // A digit can occur
        // if the string is empty
        // if the last key is
        //  an operator,
        //  a digit,
        //  a decimal,
        //  a left parenthesis,

        val isLastKeyDecimalOrLeftParenthesis = arguments.lastChar in listOf(
            btnDecimal.type.symbol,
            btnLeftParen.type.symbol
        )

        if (
            arguments.isEmpty
            || isLastKeyDecimalOrLeftParenthesis
            || arguments.isLastCharDigit
            || CalcOperator.isOperator(arguments.lastChar)
        ) {
            arguments.addToArguments(it.symbol)
        }
    }

    val btnClear = ButtonModel(
        type = ButtonType.Misc(MiscButton.Clear),
        _onClick = { arguments.clear() }
    )

    val btnLeftParen = ButtonModel(
        type = ButtonType.Misc(MiscButton.LeftParen),
        _onClick = {
            // a left parenthesis is valid if
            // `arguments` is empty OR
            // the last key is an operator OR
            // the last key is a left parenthesis

            if (
                arguments.isEmpty
                || CalcOperator.isOperator(arguments.lastChar)
                || arguments.lastChar == it.symbol
            ) {
                arguments.addToArguments(it.symbol)
            }
        }
    )

    val btnRightParen = ButtonModel(
        type = ButtonType.Misc(MiscButton.RightParen),
        _onClick = {
            // A right parenthesis must have an unmatched left parenthesis
            if (isUnmatchedLeftParenExist(arguments.args.value!!)) {
                // Then the last key should either be a digit or a right parenthesis
                if (
                    arguments.isLastCharDigit ||
                    (arguments.lastChar == it.symbol)
                ) {
                     arguments.addToArguments(it.symbol)
                }
            }
        }
    )

    val btnDelete = ButtonModel(
        type = ButtonType.Misc(MiscButton.Delete),
        _onClick = { arguments.deleteLastChar() }
    )

    val btnDecimal = ButtonModel(
        type = ButtonType.Misc(MiscButton.Decimal),
        _onClick = {
            // zero should be prefixed to a decimal if `arguments.args` is empty
            // or an expression has just been solved
            if (arguments.isEmpty) {
                arguments.addToArguments("${btnZero.type.symbol}${it.symbol}")
            } else if ( arguments.isLastCharDigit &&
                !isCharExistAfterLastOperator(
                    char = it.symbol,
                    arguments = arguments.args.value!!
                )
            ) {
                arguments.addToArguments(it.symbol)
            }
        }
    )

    private val onOperatorClick: (ButtonType) -> Unit = {
        // An operator should be after a digit or a right parenthesis ')'
        val isLastKeyDigitOrRightParen = arguments.isLastCharDigit ||
                (arguments.lastChar == btnRightParen.type.symbol)
        // If the last key is a different operator and it is not the first argument, it should be replaced
        val shouldReplaceLastOperator = CalcOperator.isOperator(arguments.lastChar) &&
            arguments.isEmpty &&
            (arguments.lastChar != it.symbol)

        val isOperatorSubtractAndFirstArg = (it.symbol == btnSubtract.type.symbol) &&
                (arguments.isEmpty || (arguments.lastChar == btnLeftParen.type.symbol))


        if (isLastKeyDigitOrRightParen || isOperatorSubtractAndFirstArg) {
            arguments.addToArguments(it.symbol)
        } else if (shouldReplaceLastOperator) {
            arguments.replaceLastChar(it.symbol)
        }
    }

    val btnDivide = ButtonModel(
        type = ButtonType.Operation(CalcOperator.Divide),
        _onClick = onOperatorClick
    )

    val btnMultiply = ButtonModel(
        type = ButtonType.Operation(CalcOperator.Multiply),
        _onClick = onOperatorClick
    )

    val btnSubtract = ButtonModel(
        type = ButtonType.Operation(CalcOperator.Subtract),
        _onClick = onOperatorClick
    )

    val btnAdd = ButtonModel(
        type = ButtonType.Operation(CalcOperator.Add),
        _onClick = onOperatorClick
    )

    val btnEquals = ButtonModel(
        type = ButtonType.Equals,
        _onClick = {
            if (isUnmatchedLeftParenExist(arguments.args.value!!) ) {
                setToastMessage("Error: Unclosed parentheses")
            } else if (arguments.isLastCharDigit || (arguments.lastChar == btnRightParen.type.symbol)) {
                arguments.solveArgs()
            } else {
                setToastMessage("Invalid Operation")
            }
        }
    )

    val btnZero = ButtonModel(
        type = ButtonType.Digit(0),
        _onClick = onDigitClick
    )

    val btnOne = ButtonModel(
        type = ButtonType.Digit(1),
        _onClick = onDigitClick
    )

    val btnTwo = ButtonModel(
        type = ButtonType.Digit(2),
        _onClick = onDigitClick
    )

    val btnThree = ButtonModel(
        type = ButtonType.Digit(3),
        _onClick = onDigitClick
    )

    val btnFour = ButtonModel(
        type = ButtonType.Digit(4),
        _onClick = onDigitClick
    )

    val btnFive = ButtonModel(
        type = ButtonType.Digit(5),
        _onClick = onDigitClick
    )

    val btnSix = ButtonModel(
        type = ButtonType.Digit(6),
        _onClick = onDigitClick
    )

    val btnSeven = ButtonModel(
        type = ButtonType.Digit(7),
        _onClick = onDigitClick
    )

    val btnEight = ButtonModel(
        type = ButtonType.Digit(8),
        _onClick = onDigitClick
    )

    val btnNine = ButtonModel(
        type = ButtonType.Digit(9),
        _onClick = onDigitClick
    )

}