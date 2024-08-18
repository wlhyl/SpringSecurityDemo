import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus

fun responseWriter(
    request: HttpServletRequest, response: HttpServletResponse, code: HttpStatus, message: String, data: String = ""
): Unit {
    response.contentType = "application/json"
    response.characterEncoding = "UTF-8"
    response.status = code.value()
    val result = HashMap<String, String>()
    result.put("message", "$message,$data")
    val jsonObject = jacksonObjectMapper()
    val json = jsonObject.writeValueAsString(result)
    response.writer.println(json)
}


