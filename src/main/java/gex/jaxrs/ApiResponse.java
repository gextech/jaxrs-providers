package gex.jaxrs;

import gex.commons.exception.GenericException;
import gex.commons.exception.ObjectNotFoundException;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.*;

/**
 * Created by domix on 12/24/14.
 */
public class ApiResponse {
  public static Response ok(Object entity) {
    return Response.status(OK)
      .entity(entity)
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale())
      .build();
  }

  public static Response created(Object entity) {
    return Response.status(CREATED)
      .entity(entity)
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale())
      .build();
  }

  public static Response noContent() {
    return Response.status(NO_CONTENT)
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale())
      .build();
  }

  public static Response unprocessableEntity(String message) {
    return unprocessableEntity(message, emptyList());
  }

  public static Response unprocessableEntity(String message, List validationErrors) {
    return unprocessableEntity(message, validationErrors, "");
  }

  public static Response unprocessableEntity(String message, List validationErrors, String code) {
    return unprocessableEntity(message, validationErrors, code, emptyMap());
  }

  public static Response unprocessableEntity(String message, List validationErrors, String code, Map extraData) {
    return Response.status(422)
      .entity(genericError(message, validationErrors, code, extraData))
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale())
      .build();
  }

  public static Response badRequest(String message) {
    return Response.status(BAD_REQUEST)
      .entity(genericError(message))
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale())
      .build();
  }

  public static Response badRequestFromException(GenericException e) {
    return Response.status(BAD_REQUEST)
      .entity(getObjectError(e))
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale())
      .build();
  }

  private static Map<String, Object> getObjectError(GenericException e) {
    Map<String, Object> error = new HashMap<>();
    error.put("message", e.getMessage());
    error.put("validationErrors", e.getValidationErrors());
    error.put("extraData", e.getExtraData());
    error.put("i18nCode", e.getI18nCode());
    error.put("localizedMessage", e.getLocalizedMessage());
    return error;
  }

  public static Response badRequestWithValidationErrors(String message, List validationErrors) {
    return Response.status(BAD_REQUEST)
      .entity(genericError(message, validationErrors))
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale())
      .build();
  }

  public static Response badRequestWithExtraData(String message, Map<String, Object> extraData) {
    return Response.status(BAD_REQUEST)
      .entity(genericError(message, emptyList(), "", extraData))
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale())
      .build();
  }

  public static Response notFound(String message) {
    return notFoundResponseBuilder(genericError(message)).build();
  }

  public static Response notFoundFromException(GenericException e) {
    return Response.status(NOT_FOUND)
      .entity(getObjectError(e))
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale())
      .build();
  }

  public static Response notFoundFromException(ObjectNotFoundException e) {
    Map<String, Object> objectError = getObjectError(e);

    objectError.put("identifier", e.getIdentifier());
    objectError.put("entityName", e.getEntityName());

    return Response.status(NOT_FOUND)
      .entity(objectError)
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale())
      .build();
  }

  public static Response.ResponseBuilder notFoundResponseBuilder(String message) {
    return notFoundResponseBuilder(genericError(message));
  }

  public static Response.ResponseBuilder notFoundResponseBuilder(Object entity) {
    return Response.status(NOT_FOUND)
      .entity(entity)
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale());
  }


  public static Response notFoundExtraData(String message, Map<String, Object> info) {
    return Response.status(NOT_FOUND)
      .entity(genericError(message, emptyList(), "", info))
      .type(APPLICATION_JSON_TYPE)
      .language(LocaleContextHolder.getLocale())
      .build();
  }

  public static Map<String, Object> genericError(String message) {
    return genericError(message, emptyList());
  }

  public static Map<String, Object> genericError(String message, List validationErrors) {
    return genericError(message, validationErrors, "");
  }

  public static Map<String, Object> genericError(String message, List validationErrors, String code) {
    return genericError(message, validationErrors, code, emptyMap());
  }

  public static Map<String, Object> genericError(String message, List validationErrors, String code, Map extraData) {
    return genericError(message, validationErrors, code, extraData, "");
  }

  public static Map<String, Object> genericError(String message, List validationErrors, String code, Map extraData, String i18nCode) {
    Map<String, Object> error = new HashMap<>();
    error.put("message", message);
    error.put("validationErrors", validationErrors);
    error.put("code", code);
    error.put("extraData", extraData);
    error.put("i18nCode", i18nCode);
    return error;
  }

}
