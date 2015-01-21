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
package gex.jaxrs;

import gex.commons.exception.GenericException;
import gex.commons.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.*;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

/**
 * Created by domix on 12/24/14.
 */
@Component
public class ApiResponse {
  public Response ok(Object entity) {
    return buildResponse(OK.getStatusCode(), entity);
  }

  public Response created(Object entity) {
    return buildResponse(CREATED.getStatusCode(), entity);
  }

  public Response noContent() {
    return buildResponse(NO_CONTENT.getStatusCode(), null);
  }

  public Response unprocessableEntity(String message) {
    return unprocessableEntity(message, emptyList());
  }

  public Response unprocessableEntity(String message, List validationErrors) {
    return unprocessableEntity(message, validationErrors, "");
  }

  public Response unprocessableEntity(String message, List validationErrors, String code) {
    return unprocessableEntity(message, validationErrors, code, emptyMap());
  }

  public Response unprocessableEntity(String message, List validationErrors, String code, Map extraData) {
    Map<String, Object> entity = genericError(message, validationErrors, code, extraData);
    return buildResponse(422, entity);
  }

  public Response buildResponse(Integer status, Object entity) {
    return Response.status(status).entity(entity).type(APPLICATION_JSON_TYPE).language(getLocale()).build();
  }

  public Response badRequest(String message) {
    Map<String, Object> entity = genericError(message);
    return buildResponse(BAD_REQUEST.getStatusCode(), entity);
  }

  public Response badRequestFromException(GenericException e) {
    Map<String, Object> entity = getObjectError(e);
    return buildResponse(BAD_REQUEST.getStatusCode(), entity);
  }

  private Map<String, Object> getObjectError(GenericException e) {
    return genericError(e.getMessage(), e.getValidationErrors(), e.getCode(), e.getExtraData(), e.getI18nCode());
  }

  public Response badRequestWithValidationErrors(String message, List validationErrors) {
    Map<String, Object> entity = genericError(message, validationErrors);
    return buildResponse(BAD_REQUEST.getStatusCode(), entity);
  }

  public Response badRequestWithExtraData(String message, Map<String, Object> extraData) {
    Map<String, Object> entity = genericError(message, emptyList(), "", extraData);
    return buildResponse(BAD_REQUEST.getStatusCode(), entity);
  }

  public Response notFound(String message) {
    Map<String, Object> entity = genericError(message);
    return buildResponse(NOT_FOUND.getStatusCode(), entity);
  }

  public Response notFoundFromException(GenericException e) {
    Map<String, Object> entity = getObjectError(e);
    return buildResponse(NOT_FOUND.getStatusCode(), entity);
  }

  public Response notFoundFromException(ObjectNotFoundException e) {
    Map<String, Object> objectError = getObjectError(e);
    objectError.put("identifier", e.getIdentifier());
    objectError.put("entityName", e.getEntityName());

    return buildResponse(NOT_FOUND.getStatusCode(), objectError);
  }

  public Response notFoundExtraData(String message, Map<String, Object> info) {
    Map<String, Object> entity = genericError(message, emptyList(), "", info);
    return buildResponse(NOT_FOUND.getStatusCode(), entity);
  }

  public Map<String, Object> genericError(String message) {
    return genericError(message, emptyList());
  }

  public Map<String, Object> genericError(String message, List validationErrors) {
    return genericError(message, validationErrors, "");
  }

  public Map<String, Object> genericError(String message, List validationErrors, String code) {
    return genericError(message, validationErrors, code, emptyMap());
  }

  public Map<String, Object> genericError(String message, List validationErrors, String code, Map extraData) {
    return genericError(message, validationErrors, code, extraData, "");
  }

  public Map<String, Object> genericError(String message, List validationErrors, String code, Map extraData, String i18nCode) {
    Map<String, Object> error = new HashMap<>();
    error.put("message", message);
    error.put("validationErrors", validationErrors);
    error.put("code", code);
    error.put("extraData", extraData);
    error.put("i18nCode", i18nCode);
    return error;
  }

}
