class Controller {
    private val service = Service()

    fun send(requests: List<Request>): Response {
        return service.send(requests)
    }
}
