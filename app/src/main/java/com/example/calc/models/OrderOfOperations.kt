package com.example.calc.models

const val numberReStr = "0-9."
const val operatorReStr = "+\\-/x"
enum class OrderOfOperations(val regex: Regex) {

    //    Parentheses("\\([$operatorReStr$numberReStr]+\\)".toRegex()),
    Parentheses("\\([$operatorReStr$numberReStr]+\\)".toRegex()),
    MultiplicationOrDivision("-?[$numberReStr]+[x/][$numberReStr]+".toRegex()),
    AdditionOrSubtract("-?[$numberReStr]+[+\\-][$numberReStr]+".toRegex());

    companion object {
        val valuesAsList = values().toMutableList()
    }
}