data class Response(
    val status: Int,
    val message: String,
    val errors: List<Result.Error>
) {
    companion object {
        const val SUCCESS = 200
        const val SUCCESS_MESSAGE = "Success"
        const val BAD_REQUEST = 400
        const val BAD_REQUEST_MESSAGE = "Bad Request"

        fun success() = Response(SUCCESS, SUCCESS_MESSAGE, emptyList())

        fun badRequest(errors: List<Result.Error>) = Response(BAD_REQUEST, BAD_REQUEST_MESSAGE, errors)
    }
}
