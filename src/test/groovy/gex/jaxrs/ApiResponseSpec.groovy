/*
 *
 * Copyright (C) 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
