package gex.jaxrs.provider

import gex.commons.exception.ObjectNotFoundException
import gex.jaxrs.ApiResponse
import spock.lang.Specification

import javax.ws.rs.core.Response

/**
 * Created by domix on 12/26/14.
 */
class ObjectNotFoundExceptionExceptionMapperSpec extends Specification {
  def 'should catch the ObjectNotFoundException'() {
    setup:
      ApiResponse apiResponse = Spy(ApiResponse)
      apiResponse.buildResponse(_, _) >> Mock(Response)
      def exception = new ObjectNotFoundException('id', 'fail')
      def exceptionMapper = new ObjectNotFoundExceptionExceptionMapper(apiResponse: apiResponse)
    when:
      def response = exceptionMapper.toResponse(exception)
    then:
      response
  }
}
