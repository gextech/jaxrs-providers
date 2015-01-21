/**
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
package gex.jaxrs.provider;

import gex.commons.exception.GenericException;
import gex.jaxrs.ApiResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by domix on 12/25/14.
 */
@Slf4j
@Provider
public class GenericExceptionExceptionMapper implements ExceptionMapper<GenericException> {
  @Setter
  @Autowired
  private ApiResponse apiResponse;

  @Override
  public Response toResponse(GenericException exception) {
    return apiResponse.badRequestFromException(exception);
  }
}
