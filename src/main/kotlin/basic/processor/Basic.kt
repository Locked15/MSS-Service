package basic.processor

import kotlin.math.pow

class Basic(lambda: Double, tsred: Double, mu: Double, m: Double) {
    private val internalData = mutableMapOf<String, Double>()

    init {
        internalData["lambda"] = lambda
        internalData["tsred"] = tsred
        internalData["mu"] = mu
        internalData["m"] = m
    }

    private fun initBasicValue() {
        val lambda = internalData.getValue("lambda")
        val mu = internalData.getValue("mu")

        internalData["ro"] = lambda / mu
    }

    private fun initPercentM0() {
        val ro = internalData.getValue("ro")

        if (ro == 1.0) internalData["p0"] = (1 - ro) / (1 - ro.pow((internalData.getValue("m") + 2)))
        else internalData["p0"] = 1 / (internalData.getValue("m") + 2)
    }

    private fun calculatePercent(count: Int) = count * internalData.getValue("p0")

    private fun setDenyPercent() {
        internalData["potkaz"] = calculatePercent(1)
    }

    private fun setQ() {
        internalData["q"] = 1 - internalData.getValue("potkaz")
    }

    private fun SetPSystem() {
        internalData["psystem"] = 1 - internalData.getValue("potkaz")
    }

    private fun setA() {
        internalData["A"] = internalData.getValue("lambda") * internalData.getValue("q")
    }

    private fun setNoChered() {
        val m = internalData.getValue("m")
        val ro = internalData.getValue("ro")
        val p0 = internalData.getValue("p0")
        val result = getResultForOchered(m, ro, p0)

        internalData["nochered"] = result
    }

    private fun getResultForOchered(m: Double, ro: Double, p0: Double): Double {
        var result = 0.0
        if (m == 1.0) {
            val chislitel = ro * ro * (1 - (ro.pow(m)) * (m + 1 - m * ro))
            val znamenatel = (1 - ro.pow(m - 2)) * (1 - ro)
            result = p0 * chislitel / znamenatel
        }
        else {
            result = m * (m + 1) / (2 * m + 4)
        }

        return result
    }

    private fun setNoBSL() {
        internalData["nobsl"] = internalData.getValue("ro") * internalData.getValue("q")
    }

    private fun setNSystem() {
        internalData["nsystem"] = internalData.getValue("nochered") + internalData.getValue("nobsl")
    }

    private fun setTochered() {
        internalData["tochered"] = internalData.getValue("nochered") /
                (internalData.getValue("lambda") * internalData.getValue("psystem"))
    }

    private fun setTsystem() {
        internalData["tsystem"] = internalData.getValue("nsystem") /
                (internalData.getValue("lambda") * internalData.getValue("psystem"))
    }

    fun setAllParams() {
        initBasicValue()
        initPercentM0()
        setDenyPercent()
        setQ()
        SetPSystem()
        setA()
        setNoChered()
        setNoBSL()
        setNSystem()
        setTochered()
        setTsystem()
    }

    fun getResult() {
        val result = String.format(ToStringTemplate,
                                   internalData["ro"],
                                   internalData["p0"],
                                   internalData["potkaz"],
                                   internalData["q"],
                                   internalData["q"],
                                   internalData["psystem"],
                                   internalData["A"],
                                   internalData["nochered"],
                                   internalData["nobsl"],
                                   internalData["nsystem"],
                                   internalData["nsystem"],
                                   internalData["tochered"],
                                   internalData["tsystem"]
        )

        println(result)
    }

    companion object {

        private const val ToStringTemplate =
            """
                Коэф. использования объекта: %.3f;
                Вер. свободы линии: %.3f;
                Вер. отказа: %.3f;
                Вер. принятия заявки: %.3f;
                Отн. пропуск. способность: %.3f;
                Абс. пропуск. способность: %.3f;
                Ср. число заявок в очереди: %.3f;
                Ср. число заявок в обслуживании: %.3f;
                Ср. число заявок в СМО: %.3f;
                Ср. время ожидания: %.3f;
                Ср. время пребывания: %.3f.
                
                Сами Вы выбрали это место, или его выбрали за Вас —
                Это лучшее место из оставшихся...
            """
    }
}
