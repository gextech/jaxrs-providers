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

import gex.commons.exception.EntityValidationException;
import gex.jaxrs.ApiResponse;
import gex.jaxrs.provider.support.SpringErrorsExtractor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.Errors;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.springframework.util.ReflectionUtils.*;

/**
 * Created by domix on 2/18/15.
 */
@Slf4j
@Provider
public class EntityValidationExceptionMapper implements ExceptionMapper<EntityValidationException> {
  @Setter
  @Autowired
  private ApplicationContext applicationContext;
  @Setter
  @Autowired
  private ApiResponse apiResponse;

  @Override
  public Response toResponse(EntityValidationException exception) {
    log.debug(apiResponse.getClass().getName());
    Object entity = exception.getEntity();

    Errors errors = getErrors(entity);
    if (errors != null) {
      List<String> errorList = new SpringErrorsExtractor().extractError(errors, applicationContext);
      return apiResponse.unprocessableEntity(exception.getMessage(), errorList);
    }

    return apiResponse.unprocessableEntity(exception.getMessage());
  }

  private Errors getErrors(Object entity) {
    if (entity instanceof Errors) {
      return (Errors) entity;
    }

    Errors errors = getErrorsFromField(entity);

    return errors != null ? errors : getErrorFromGetter(entity);
  }

  private Errors getErrorsFromField(Object entity) {
    Field errorsField = findField(entity.getClass(), "errors", Errors.class);
    Errors errors = null;
    if (errorsField != null) {
      makeAccessible(errorsField);
      errors = (Errors) getField(errorsField, entity);
    }
    return errors;
  }

  private Errors getErrorFromGetter(Object entity) {
    Errors errors = null;
    Method errorsMethod = findMethod(entity.getClass(), "getErrors");
    if (errorsMethod != null) {
      makeAccessible(errorsMethod);
      errors = (Errors) invokeMethod(errorsMethod, entity);
    }

    return errors;
  }
}
