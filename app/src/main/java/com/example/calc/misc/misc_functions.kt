package com.example.calc.misc

import com.example.calc.models.CalcOperator
import com.example.calc.models.OrderOfOperations
import com.example.calc.models.numberReStr
import com.example.calc.models.operatorReStr

fun isUnmatchedLeftParenExist(str: String): Boolean {
    val leftParen = '('
    val rightParen = ')'

    val list = mutableListOf<Char>()
    for (ch in str) {
        if (ch  == leftParen) {
            list.add(ch)
        } else if (
            (ch == rightParen)
            && list.isNotEmpty()
            && (list.last() == leftParen)
        ) {
            list.removeLast()
        }
    }

    return leftParen in list
}

/**
 * This function checks if a particular character has occurred before the
 * last operator in [arguments]. If no operator is found, it returns true
 * */
fun isCharExistAfterLastOperator(char: String, arguments: String): Boolean {
    for (i in arguments.indices.reversed()) {
        val kn = arguments[i].toString()
        if (kn == char) {
            return true // char exists before operator
        }
        if (CalcOperator.isOperator(kn)) {
            return false // char does not exist before operator
        }
    }
    return false // No operator found, char can not exist
}

/**
 * A unit expression is one that contains two number and one operator
 * i.e. 34-2, 7.1-6.0
 * [performOperation] confirms the string passed is unit expression and returns
 * the result of the operation
 * */
fun performOperation(unitExp: String): Float {

    val operatorRegex = "(?<!^)[$operatorReStr]".toRegex()
    val regex = "^-?[$numberReStr]+[$operatorReStr][$numberReStr]+".toRegex()
    if (!unitExp.matches(regex)) throw Exception("$unitExp is invalid, " +
            "it should contain one operator enclosed by two numbers")

    val (num1, num2) = unitExp.split(operatorRegex).map { it.toFloat() }
    val operator = operatorRegex.find(unitExp)!!.value

    return when(operator) {
        CalcOperator.Divide.symbol -> num1 / num2
        CalcOperator.Multiply.symbol -> num1 * num2
        CalcOperator.Add.symbol -> num1 + num2
        CalcOperator.Subtract.symbol -> num1 - num2
        else -> throw Exception("Invalid operator")
    }
}

fun areEnclosingParenthesesMatching(str: String): Boolean {
    val leftParenthesis = '('
    val rightParenthesis = ')'

    val list = mutableListOf<Pair<Char, Int>>()
    for ((index, ch) in str.withIndex()) {
        if (ch == leftParenthesis) {
            list.add(Pair(ch, index))
        } else if (ch == rightParenthesis){
            val (lastPairChar, lastPairIndex) = list.last()
            if (lastPairChar == leftParenthesis) {
                if (index == str.lastIndex && (lastPairIndex == 0)) {
                    return true
                }
                list.removeLast()
            }
        }
    }
    return false
}

/**
 * This function removes the enclosing parentheses from [str]
 * parentheses are only removed if the first character is `(`
 * and the last character is ')'
 * */
fun removeBookendParentheses(str: String): String {
    return if (str.first() == '(' && str.last() == ')') {
        str.substring(1, str.lastIndex)
    } else str
}

fun solveExpression(
    _exp: String,
//    opsPerformed: Array<OrderOfOperations> = OrderOfOperations.values()
): String {
    var exp = if (areEnclosingParenthesesMatching(_exp))
        removeBookendParentheses(_exp)
    else _exp

    val opsToPerform = OrderOfOperations.valuesAsList
    for (op in opsToPerform) {
        while (op.regex.containsMatchIn(exp)) {
            println("$op, $exp")
            exp = if (op == OrderOfOperations.Parentheses) {
                exp.replace(op.regex) {
                    solveExpression(it.value)
                }
            } else {
                exp.replace(op.regex) {
                    performOperation(it.value).toString()
                }
            }
        }
    }
    return exp
}

fun main(args: Array<String>) {
    val exp = "5x4+3x(9-6)"
    val result = solveExpression(exp)
    println(result)
}
