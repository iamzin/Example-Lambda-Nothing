class Result {
    open class Error(
        open val code: ErrorCode,
        open val message: String
    )

    data class ReceiverError(
        val receiver: String,
        override val code: ReceiverErrorCode,
        override val message: String
    ): Error(code, message) {
        companion object {
            fun notFound(receiver: String) =
                ReceiverError(receiver, ReceiverErrorCode.RECEIVER_NOT_FOUND, ReceiverErrorCode.RECEIVER_NOT_FOUND.message)

            fun invalid(receiver: String) =
                ReceiverError(receiver, ReceiverErrorCode.RECEIVER_INVALID, ReceiverErrorCode.RECEIVER_INVALID.message)
        }
    }

    enum class ReceiverErrorCode(override val message: String): ErrorCode {
        RECEIVER_NOT_FOUND("Receiver not found."),
        RECEIVER_INVALID("Receiver is invalid."),
    }

    data class TemplateError(
        val templateCode: String,
        override val code: TemplateErrorCode,
        override val message: String
    ): Error(code, message) {
        companion object {
            fun notFound(templateCode: String) =
                TemplateError(templateCode, TemplateErrorCode.TEMPLATE_NOT_FOUND, TemplateErrorCode.TEMPLATE_NOT_FOUND.message)

            fun invalid(templateCode: String) =
                TemplateError(templateCode, TemplateErrorCode.TEMPLATE_INVALID, TemplateErrorCode.TEMPLATE_INVALID.message)
        }
    }

    enum class TemplateErrorCode(override val message: String): ErrorCode {
        TEMPLATE_NOT_FOUND("Template not found."),
        TEMPLATE_INVALID("Template is invalid."),
    }
}

interface ErrorCode {
    val message: String
}
