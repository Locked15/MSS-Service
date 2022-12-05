package basic

import basic.processor.Basic

fun main(args: Array<String>) {
    val lambda = getDoubleValue("Введите базовое значение вычисления (1)")
    val tsred = getDoubleValue("Введите базовое значение вычисления (2)")
    val mu = getDoubleValue("Введите базовое значение вычисления (3)")
    val m = getDoubleValue("Введите базовое значение вычисления (4)")

    val base = Basic(lambda, tsred, mu, m)
    base.setAllParams()
    base.getResult()

    println("\n\nКонец.")
}

private fun getDoubleValue(message: String): Double {
    var inputted : String
    do {
        print("${message}: ")
        inputted = readln()
    }
    while (inputted.toDoubleOrNull() == null)

    return inputted.toDouble()
}
