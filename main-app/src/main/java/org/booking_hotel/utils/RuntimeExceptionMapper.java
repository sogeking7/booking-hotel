package org.booking_hotel.utils;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.MediaType;


@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

  @Override
  public Response toResponse(RuntimeException ex) {
    ErrorMessage em = new ErrorMessage(
            500,
            "INTERNAL_SERVER_ERROR",
            ex.getMessage() != null ? ex.getMessage() : "Unexpected error"
    );

    return Response
        .status(em.getStatus())
        .entity(em)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}
