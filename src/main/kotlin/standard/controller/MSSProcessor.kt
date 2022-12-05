package standard.controller

import standard.model.MSSBaseModel
import standard.model.MSSControlModel
import kotlin.math.ln

class MSSProcessor(base: MSSBaseModel, control: MSSControlModel) {

    private val baseModel: MSSBaseModel

    private val controlModel: MSSControlModel

    init {
        baseModel = base
        controlModel = control
    }

    fun beginProcessing() {
        for (i in 0 until controlModel.repeatingCount) {
            println("Проводится итерация №${i + 1}: ")
            baseModel.generalTime = inputDouble("Укажите таргетированное время работы системы")
            // Проводим полную итерацию с учётом разных состояний.
            do {
                when (controlModel.flowIteratorDefiner) {
                    0 -> calculateOnZero()
                    1 -> calculateOnOne()

                    else -> calculateOnTwo()
                }
            } while (baseModel.systemWorkingTime < baseModel.generalTime)

            finalizeValues()
            showValues()
        }
    }

    private fun calculateOnZero() {
        baseModel.lambda = calculateLambda()
        baseModel.systemWorkingTime += baseModel.lambda

        controlModel.flowIteratorDefiner = 1
        controlModel.allRequestsCount++
        controlModel.countOfServedRequests++
    }

    private fun calculateOnOne() {
        baseModel.lambda = calculateLambda()
        baseModel.multiplier = calculateMultiplier()

        if (baseModel.lambda < baseModel.multiplier) {
            controlModel.flowIteratorDefiner = 2
            controlModel.allRequestsCount++
            controlModel.countOfServedRequests++

            baseModel.systemWorkingTime += baseModel.lambda
            baseModel.timeToServe += baseModel.lambda
        }
        else {
            controlModel.flowIteratorDefiner = 0

            baseModel.systemWorkingTime += baseModel.multiplier
            baseModel.timeToServe += baseModel.multiplier
        }
    }

    private fun calculateOnTwo() {
        baseModel.lambda = calculateLambda()
        baseModel.multiplier = calculateMultiplier()

        if (baseModel.lambda < baseModel.multiplier) {
            controlModel.flowIteratorDefiner = 2
            controlModel.allRequestsCount++
            controlModel.deniedRequestsCount++

            baseModel.timeToServe += baseModel.lambda
            baseModel.systemWorkingTime += baseModel.lambda
        }
        else {
            controlModel.flowIteratorDefiner = 1

            baseModel.systemWorkingTime += baseModel.multiplier
            baseModel.timeToServe += baseModel.multiplier
        }
    }

    private fun calculateLambda()  = -1 * ln((0..1000).random().toDouble() / 1000.0 + 0.001) /
            baseModel.inputFlowIntensity

    private fun calculateMultiplier() = -1 * ln((0..1000).random().toDouble() / 1000.0 + 0.001) /
            baseModel.serveFlowIntensity

    private fun finalizeValues() {
        baseModel.chanceToPassing = (baseModel.generalTime - baseModel.timeToServe) / 2 / baseModel.generalTime
        baseModel.chanceToDeny = controlModel.deniedRequestsCount / controlModel.allRequestsCount.toDouble()
        baseModel.servedRequestsPercent = controlModel.countOfServedRequests / controlModel.allRequestsCount.toDouble()
        baseModel.absoluteFlowBandwidth = controlModel.countOfServedRequests / baseModel.generalTime
        baseModel.averageChannelLoad = (baseModel.generalTime - baseModel.timeToServe) / baseModel.generalTime
    }

    private fun showValues() {
        val result = String.format(TO_STRING_TEMPLATE, controlModel.allRequestsCount,
                                   controlModel.countOfServedRequests,
                                   controlModel.deniedRequestsCount,
                                   baseModel.chanceToPassing,
                                   baseModel.chanceToDeny,
                                   baseModel.servedRequestsPercent,
                                   baseModel.absoluteFlowBandwidth,
                                   baseModel.averageChannelLoad
        )

        println("\nРезультат: ${result}...")
    }

    companion object {

        private const val TO_STRING_TEMPLATE =
            """
                Кол-во заявок: %d;
                Кол-во обслуженных заявок: %d;
                Число отказов: %d;
                
                Вероятность простоя: %.3f;
                Вероятность отказа: %.3f;
                
                Доля обслуженных заявок: %.2f;
                Пропускная способность: %.2f;
                Средняя загрузка каналов: %.4f.
            """

        private fun inputDouble(message: String): Double {
            var inputted: String
            do {
                print("${message}: ")
                inputted = readln()
            } while (inputted.toDoubleOrNull() == null)

            return inputted.toDouble()
        }
    }
}
