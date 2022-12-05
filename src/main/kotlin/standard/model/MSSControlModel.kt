package standard.model

class MSSControlModel(var allRequestsCount: Int, var deniedRequestsCount: Int, var countOfServedRequests: Int,
                      var flowIteratorDefiner: Int, val repeatingCount: Int) {

    constructor(repeats: Int) : this(0, 0, 0, 0, repeats)
}
