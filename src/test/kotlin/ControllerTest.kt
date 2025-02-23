import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ControllerTest {
    companion object {
        private const val RECEIVER_LENA = "01011111111"
        private const val RECEIVER_ZIN = "01022222222"
        private const val RECEIVER_NOT_FOUND = "01033333333"
        private const val RECEIVER_INVALID = "010"
        private const val TEMPLATE_WELCOME = "TEMPLATE_WELCOME"
        private const val TEMPLATE_GIFT = "TEMPLATE_GIFT"
        private const val TEMPLATE_NOT_FOUND = "TEMPLATE_NOT_FOUND"
        private const val TEMPLATE_INVALID = "TEMPLATE_INVALID"
    }

    private val controller = Controller()

    private lateinit var requests: List<Request>

    @BeforeEach
    fun setup() {
        requests = mutableListOf(
            Request(RECEIVER_LENA, TEMPLATE_WELCOME),
            Request(RECEIVER_ZIN, TEMPLATE_GIFT)
        )
    }

    @Test
    fun success() {
        val response = controller.send(requests)

        response.status shouldBe  Response.Companion.SUCCESS
        response.message shouldBe Response.Companion.SUCCESS_MESSAGE
        response.errors shouldHaveSize 0
    }

    @Test
    fun receiver_errors() {
        val notFoundReceiver = Request(RECEIVER_NOT_FOUND, TEMPLATE_WELCOME)
        val invalidReceiver = Request(RECEIVER_INVALID, TEMPLATE_GIFT)

        val badRequests = requests + notFoundReceiver + invalidReceiver

        val response = controller.send(badRequests)

        response.status shouldBe  Response.Companion.BAD_REQUEST
        response.message shouldBe Response.Companion.BAD_REQUEST_MESSAGE
        response.errors shouldHaveSize 2
        response.errors shouldContain Result.ReceiverError.notFound(RECEIVER_NOT_FOUND)
        response.errors shouldContain Result.ReceiverError.invalid(RECEIVER_INVALID)
    }

    @Test
    fun template_errors() {
        val notFoundTemplate = Request(RECEIVER_LENA, TEMPLATE_NOT_FOUND)
        val invalidTemplate = Request(RECEIVER_ZIN, TEMPLATE_INVALID)

        val badRequests = requests + notFoundTemplate + invalidTemplate

        val response = controller.send(badRequests)

        response.status shouldBe  Response.Companion.BAD_REQUEST
        response.message shouldBe Response.Companion.BAD_REQUEST_MESSAGE
        response.errors shouldHaveSize 2
        response.errors shouldContain Result.TemplateError.notFound("TEMPLATE_NOT_FOUND")
        response.errors shouldContain Result.TemplateError.invalid("TEMPLATE_INVALID")
    }
}
