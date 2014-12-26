package gex.jaxrs.provider

import gex.commons.exception.GenericException
import gex.jaxrs.ApiResponse
import spock.lang.Specification

import javax.ws.rs.core.Response

/**
 * Created by domix on 12/26/14.
 */
class GenericExceptionExceptionMapperSpec extends Specification {
  def 'should catch the GenericException'() {
    setup:
      ApiResponse apiResponse = Spy(ApiResponse)
      apiResponse.buildResponse(_, _) >> Mock(Response)
      def exception = new GenericException('fail')
      def exceptionMapper = new GenericExceptionExceptionMapper(apiResponse: apiResponse)
    when:
      def response = exceptionMapper.toResponse(exception)
    then:
      response
  }
}
