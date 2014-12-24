package gex.jaxrs.provider.support

import org.springframework.context.MessageSource
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.context.i18n.LocaleContextHolder.getLocale

/**
 * Created by domix on 12/23/14.
 */
class SpringErrorsExtractorSpec extends Specification {
  @Shared
  def args = ['hola']
  @Shared
  def codes = ['code1', 'code2', 'code3']

  @Unroll
  def 'fail when errors is #errors and messageSource is #messageSource'() {
    when:
      new SpringErrorsExtractor().extractError(errors, messageSource)
    then:
      IllegalArgumentException e = thrown()
      e
    where:
      errors       | messageSource
      null         | null
      Mock(Errors) | null
      null         | Mock(MessageSource)
  }

  def 'should found the first message using the fieldError codes'() {
    setup:
      def fieldError = Stub(FieldError)
      fieldError.getArguments() >> args
      fieldError.getCodes() >> codes

      def errors = Stub(Errors)
      errors.getFieldErrors() >> {
        [fieldError]
      }

      def messageSource = Stub(MessageSource)
      messageSource.getMessage('code1', args, '', getLocale()) >> ''
      messageSource.getMessage('code2', args, '', getLocale()) >> 'message'
      messageSource.getMessage('code3', args, '', getLocale()) >> 'message3'
    when:
      def errorList = new SpringErrorsExtractor().extractError(errors, messageSource)
    then:
      errorList
      errorList.size() == 1
      errorList.first() == 'message'
  }

  def 'should found the message with the fieldError code'() {
    setup:
      def fieldError = Stub(FieldError)
      fieldError.getArguments() >> args
      fieldError.getCodes() >> codes
      fieldError.getCode() >> 'theCode'


      def errors = Stub(Errors)
      errors.getFieldErrors() >> {
        [fieldError]
      }

      def messageSource = Stub(MessageSource)
      messageSource.getMessage('code1', args, '', getLocale()) >> ''
      messageSource.getMessage('code2', args, '', getLocale()) >> ''
      messageSource.getMessage('code3', args, '', getLocale()) >> ''

      messageSource.getMessage('theCode', args, '', getLocale()) >> 'message'
    when:
      def errorList = new SpringErrorsExtractor().extractError(errors, messageSource)
    then:
      errorList
      errorList.size() == 1
      errorList.first() == 'message'
  }

  def 'should found the message with the fieldError defaultMessage'() {
    setup:
      def fieldError = Stub(FieldError)
      fieldError.getArguments() >> args
      fieldError.getCodes() >> codes
      fieldError.getCode() >> 'theCode'
      fieldError.getDefaultMessage() >> 'message'


      def errors = Stub(Errors)
      errors.getFieldErrors() >> {
        [fieldError]
      }

      def messageSource = Stub(MessageSource)
      messageSource.getMessage('code1', args, '', getLocale()) >> ''
      messageSource.getMessage('code2', args, '', getLocale()) >> ''
      messageSource.getMessage('code3', args, '', getLocale()) >> ''

      messageSource.getMessage('theCode', args, fieldError.getDefaultMessage(), getLocale()) >> 'message'
    when:
      def errorList = new SpringErrorsExtractor().extractError(errors, messageSource)
    then:
      errorList
      errorList.size() == 1
      errorList.first() == 'message'
  }
}
