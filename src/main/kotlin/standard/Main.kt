package standard

import standard.controller.MSSProcessor
import standard.model.MSSBaseModel
import standard.model.MSSControlModel

fun main(args: Array<String>) {
    val intensity = getDoubleValue("Введите интенсивность входного потока")
    val serveIntensity = getDoubleValue("Введите интенсивность потока обслуживания")
    val repeats = getIntegerValue("Введите количество повторений")

    val mainModel = MSSBaseModel(intensity, serveIntensity)
    val controlModel = MSSControlModel(repeats)

    val processor = MSSProcessor(mainModel, controlModel)
    processor.beginProcessing()

    println("\n\nКонец.")
}

private fun getIntegerValue(message: String): Int {
    var inputted : String
    do {
        print("${message}: ")
        inputted = readln()
    }
    while (inputted.toIntOrNull() == null)

    return inputted.toInt()
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
