package org.booking_hotel.utils;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.MediaType;

@Provider

public class CountryErrorMapper implements ExceptionMapper<DublicateCountryException> {

    @Override
    public Response toResponse(DublicateCountryException de) {
        ErrorMessage em = new ErrorMessage(de.getStatus(), de.getMessage(), de.getDescription());
        return Response
                .status(em.getStatus())
                .entity(em)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}


