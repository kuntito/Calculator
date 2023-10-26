package com.example.calc.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calc.misc.solveExpression
import com.example.calc.viewModelTag

class CalcArguments {
    var args: MutableLiveData<String> = MutableLiveData("")
        private set

    private var isArgsSolved: Boolean = false

    private var lastResult: String = ""

    var lastChar: String? = null
        get() = args.value!!.lastOrNull()?.toString()
        private set

    val isLastCharDigit: Boolean
        get() = lastChar?.toIntOrNull() != null

    var isEmpty: Boolean = false
        get() = args.value!!.isEmpty()
        private set

    private fun postValue(str: String) {
        var newStr = str
        if (isArgsSolved) {
            if (!CalcOperator.isOperator(str.lastOrNull().toString())) {
                newStr = str.removePrefix(lastResult)
            }
        }

        newStr = if (newStr.first() == '.') "0${newStr}" else newStr

        args.postValue(newStr)
        negateArgsSolved()
    }

    fun clear() {
        args.postValue("")
        negateArgsSolved()
    }

    fun deleteLastChar() {
        args.postValue(args.value!!.dropLast(1))
        negateArgsSolved()
    }

    fun addToArguments(str: String) {
        postValue(args.value + str)
    }

    fun replaceLastChar(str: String) {
        args.postValue(args.value!!.dropLast(1) + str)
        negateArgsSolved()
    }

    private fun negateArgsSolved() {
        isArgsSolved = false
    }

    fun solveArgs() {
        val result = solveExpression(args.value!!).toFloat()
        val resultTo2dp = String.format("%.2f", result).toFloat()
        val modResult = if (resultTo2dp%1.0 == 0.0) resultTo2dp.toInt() else resultTo2dp
        lastResult = modResult.toString()
        postValue(lastResult)
        isArgsSolved = true
    }
}