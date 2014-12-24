package gex.jaxrs.provider

import gex.commons.exception.DomainClassValidationException
import gex.jaxrs.ApiResponse
import org.springframework.context.ApplicationContext
import spock.lang.Ignore
import spock.lang.Specification

import javax.ws.rs.core.Response

import static java.util.Collections.emptyList
import static java.util.Collections.emptyMap

/**
 * Created by domix on 12/23/14.
 */
class DomainClassValidationExceptionMapperSpec extends Specification {

  def 'should create a valid response with default message because the entity has no Errors'() {
    setup:
      def entity = [
        message         : "The provided entity has some errors.",
        validationErrors: emptyList(),
        code            : "",
        extraData       : emptyMap(),
        i18nCode        : ""
      ]

      def mockResponse = Mock(Response)
      mockResponse.getEntity() >> entity
      mockResponse.getStatus() >> 422
      ApiResponse apiResponse = Spy(ApiResponse)
      apiResponse.buildResponse(_, _) >> mockResponse
      ApplicationContext applicationContext = Mock(ApplicationContext)

      def e = new DomainClassValidationExceptionMapper(apiResponse: apiResponse, applicationContext: applicationContext)

    when:
      def response = e.toResponse(new DomainClassValidationException(new Object()))

    then:
      response
      response.entity == entity
      response.status == 422
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
