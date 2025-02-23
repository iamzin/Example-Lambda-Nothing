class Repository {
    fun findReceiver(receiver: String): String? {
        return when (receiver) {
            "01011111111" -> "Lena"
            "01022222222" -> "Zin"
            "010" -> "Invalid"
            else -> null
        }
    }

    fun findTemplate(template: String): String? {
        return when (template) {
            "TEMPLATE_WELCOME" -> "Welcome to our service, {{name}}!"
            "TEMPLATE_GIFT" -> "Congratulations, {{name}}! You got a gift!"
            "TEMPLATE_INVALID" -> ""
            else -> null
        }
    }
}
