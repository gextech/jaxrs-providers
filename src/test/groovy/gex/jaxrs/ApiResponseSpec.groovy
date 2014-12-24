package gex.jaxrs

import gex.commons.exception.GenericException
import gex.commons.exception.ObjectNotFoundException
import spock.lang.Specification

import javax.ws.rs.core.Response

import static java.util.Collections.emptyList
import static java.util.Collections.emptyMap

/**
 * Created by domix on 12/24/14.
 */
class ApiResponseSpec extends Specification {
  def 'yolo'() {
    setup:
      def apiResponse = Spy(ApiResponse)
      apiResponse.buildResponse(_, _) >> Mock(Response)
    when:
      def badRequest = apiResponse.badRequest("")
    then:
      badRequest
    when:
      def badRequestFromException = apiResponse.badRequestFromException(new GenericException(""))
    then:
      badRequestFromException
    when:
      def badRequestWithExtraData = apiResponse.badRequestWithExtraData("", emptyMap())
    then:
      badRequestWithExtraData
    when:
      def badRequestWithValidationErrors = apiResponse.badRequestWithValidationErrors("", emptyList())
    then:
      badRequestWithValidationErrors
    when:
      def notFoundFromException = apiResponse.notFoundFromException(new ObjectNotFoundException("", ""))
    then:
      notFoundFromException
    when:
      def notFoundExtraData = apiResponse.notFoundExtraData("", emptyMap())
    then:
      notFoundExtraData
    when:
      def notFound = apiResponse.notFound("")
    then:
      notFound
    when:
      def notFoundFromException1 = apiResponse.notFoundFromException(new GenericException(""))
    then:
      notFoundFromException1
    when:
      def ok = apiResponse.ok("")
    then:
      ok
    when:
      def created = apiResponse.created("")
    then:
      created
    when:
      def noContent = apiResponse.noContent()
    then:
      noContent
  }
}
