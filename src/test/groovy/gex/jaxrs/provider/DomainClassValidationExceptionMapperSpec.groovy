package gex.jaxrs.provider

import gex.commons.exception.DomainClassValidationException
import org.springframework.validation.DataBinder
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by domix on 12/23/14.
 */
@Ignore
class DomainClassValidationExceptionMapperSpec extends Specification {

  def 'dd'() {
    given:
      def e = new DomainClassValidationExceptionMapper()
      def exceptionn = new DomainClassValidationException(new Object())
      e.toResponse(exceptionn)
    expect:
      e
  }

  def 'dds'() {
    given:
      def e = new DomainClassValidationExceptionMapper()

      def domainClass = new DomainClass(name: null)

      DataBinder dataBinder = new DataBinder()

      def exceptionn = new DomainClassValidationException(domainClass)
      e.toResponse(exceptionn)
    expect:
      e
  }
}
