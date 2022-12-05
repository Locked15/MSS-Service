package standard.model

class MSSBaseModel(var lambda: Double, var multiplier: Double, var generalTime: Double, var timeToServe: Double,
                   var systemWorkingTime: Double, var inputFlowIntensity: Double, var serveFlowIntensity: Double,
                   var chanceToPassing: Double, var absoluteFlowBandwidth: Double,
                   var averageChannelLoad: Double, var chanceToDeny: Double, var servedRequestsPercent: Double) {

    constructor(baseData: Double, flowIntensity: Double) : this(0.0, 0.0, 0.0, 0.0, 0.0, baseData, flowIntensity,
                                                                0.0, 0.0, 0.0, 0.0, 0.0)
}
