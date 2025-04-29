package org.booking_hotel.utils;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DefaultErrorMapper implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException be) {
        ErrorMessage em = new ErrorMessage(be.getStatus(), be.getMessage(), be.getDescription());
        return Response
                .status(em.getStatus())
                .entity(em)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
