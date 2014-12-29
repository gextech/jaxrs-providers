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
