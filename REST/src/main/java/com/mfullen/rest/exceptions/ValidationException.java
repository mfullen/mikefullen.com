package com.mfullen.rest.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author mfullen
 */
public class ValidationException extends WebApplicationException
{
    private final int status = 400;
    private String errorMessage;
    private String developerMessage;
    private List<ValidationError> errors = new ArrayList<>();

    public ValidationException()
    {
        errorMessage = "Validation Error";
        developerMessage = "The data passed in the request was invalid. Please check and resubmit";
    }

    public ValidationException(String message)
    {
        super();
        errorMessage = message;
    }

    public ValidationException(Set<? extends ConstraintViolation<?>> violations)
    {
        this();
        for (ConstraintViolation<?> constraintViolation : violations)
        {
            ValidationError error = new ValidationError();
            error.setMessage(constraintViolation.getMessage());
            error.setPropertyName(constraintViolation.getPropertyPath().toString());
            error.setPropertyValue(constraintViolation.getInvalidValue() != null ? constraintViolation.getInvalidValue().toString() : null);
            errors.add(error);
        }
    }

    @Override
    public Response getResponse()
    {
        return Response.status(status).type(MediaType.APPLICATION_JSON_TYPE).entity(getErrorResponse()).build();
    }

    public ErrorResponse getErrorResponse()
    {
        ErrorResponse response = new ErrorResponse();
        response.setApplicationMessage(developerMessage);
        response.setConsumerMessage(errorMessage);
        response.setValidationErrors(errors);
        return response;
    }

    public class ValidationError
    {
        private String propertyName;
        private String propertyValue;
        private String message;

        public String getPropertyName()
        {
            return propertyName;
        }

        public void setPropertyName(String propertyName)
        {
            this.propertyName = propertyName;
        }

        public String getPropertyValue()
        {
            return propertyValue;
        }

        public void setPropertyValue(String propertyValue)
        {
            this.propertyValue = propertyValue;
        }

        public String getMessage()
        {
            return message;
        }

        public void setMessage(String message)
        {
            this.message = message;
        }
    }

    public class ErrorResponse
    {
        private String errorCode;
        private String consumerMessage;
        private String applicationMessage;
        private List<ValidationError> validationErrors = new ArrayList<ValidationError>();

        public String getErrorCode()
        {
            return errorCode;
        }

        public void setErrorCode(String errorCode)
        {
            this.errorCode = errorCode;
        }

        public String getConsumerMessage()
        {
            return consumerMessage;
        }

        public void setConsumerMessage(String consumerMessage)
        {
            this.consumerMessage = consumerMessage;
        }

        public String getApplicationMessage()
        {
            return applicationMessage;
        }

        public void setApplicationMessage(String applicationMessage)
        {
            this.applicationMessage = applicationMessage;
        }

        public List<ValidationError> getValidationErrors()
        {
            return validationErrors;
        }

        public void setValidationErrors(List<ValidationError> validationErrors)
        {
            this.validationErrors = validationErrors;
        }
    }
}