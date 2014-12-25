package gex.jaxrs.provider;

import gex.commons.exception.ObjectNotFoundException;
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
public class ObjectNotFoundExceptionExceptionMapper implements ExceptionMapper<ObjectNotFoundException> {
  @Setter
  @Autowired
  private ApiResponse apiResponse;

  @Override
  public Response toResponse(ObjectNotFoundException exception) {
    return apiResponse.notFoundFromException(exception);
  }
}
