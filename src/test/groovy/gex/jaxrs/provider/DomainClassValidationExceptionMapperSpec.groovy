package gex.jaxrs.provider

import gex.commons.exception.DomainClassValidationException
import gex.jaxrs.ApiResponse
import spock.lang.Ignore
import spock.lang.Specification

import javax.ws.rs.core.Response

/**
 * Created by domix on 12/23/14.
 */
class DomainClassValidationExceptionMapperSpec extends Specification {

  def 'should create a valid response with default message because the entity has no Errors'() {
    setup:

      /*Map<String, Object> error = new HashMap<>();
      error.put("message", message);
      error.put("validationErrors", validationErrors);
      error.put("code", code);
      error.put("extraData", extraData);
      error.put("i18nCode", i18nCode);*/

      def entity = [
        message: "The provided entity has some errors."
      ]

      def mockResponse = Mock(Response)
      mockResponse.getEntity() >> entity
      ApiResponse apiResponse = Spy(ApiResponse)
      apiResponse.buildResponse(_, _) >> mockResponse

      def e = new DomainClassValidationExceptionMapper(apiResponse: apiResponse)

    when:
      def response = e.toResponse(new DomainClassValidationException(new Object()))

    then:
      response
      //response.entity == entity
      //response.status == 422
  }

  @Ignore
  def 'dds'() {
    given:
      def e = new DomainClassValidationExceptionMapper()

      def domainClass = new DomainClass(name: null)

      def exceptionn = new DomainClassValidationException(domainClass)
      e.toResponse(exceptionn)
    expect:
      e
  }
}
