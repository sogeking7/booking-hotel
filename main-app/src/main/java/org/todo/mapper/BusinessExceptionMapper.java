package org.todo.mapper;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.todo.dto.ErrorMessage;
import org.todo.exception.BusinessException;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException exception) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setStatusCode(exception.getStatus());
        errorMessage.setMessage(exception.getMessage());

        return Response
                .status(errorMessage.getStatusCode())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();

    }
}
