class Service {
    private val repository = Repository()

    fun send(requests: List<Request>): Response {
        val errors = mutableListOf<Result.Error>()

        requests.forEach { request ->
            validateReceiver(request.receiver) { error ->
                errors.add(error)
                return@forEach
            }

            validateTemplate(request.template) { error ->
                errors.add(error)
                return@forEach
            }
        }

        return if (errors.isEmpty()) {
            Response.success()
        } else {
            Response.badRequest(errors)
        }
    }

    private inline fun validateReceiver(receiver: String, onError: (Result.ReceiverError) -> Unit) {
        repository.findReceiver(receiver)
            ?: onError(Result.ReceiverError.notFound(receiver)) as Nothing

        if (invalidReceiver(receiver)) {
            onError(Result.ReceiverError.invalid(receiver))
        }
    }

    private inline fun validateTemplate(templateCode: String, onError: (Result.TemplateError) -> Unit) {
        val template = repository.findTemplate(templateCode)
            ?: onError(Result.TemplateError.notFound(templateCode)) as Nothing

        if (invalidTemplate(template)) {
            onError(Result.TemplateError.invalid(templateCode))
        }
    }

    private fun invalidReceiver(receiver: String): Boolean {
        return receiver.isBlank() || receiver.contains("-") || receiver.length < 11
    }

    private fun invalidTemplate(template: String): Boolean {
        return template.isBlank() || template.length > 100
    }
}
