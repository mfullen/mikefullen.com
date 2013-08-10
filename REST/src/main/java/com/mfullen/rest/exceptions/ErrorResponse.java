

package com.mfullen.rest.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mfullen
 */
public class ErrorResponse {
    private String errorCode;
    private String consumerMessage;
    private String applicationMessage;
    private List<ValidationException.ValidationError> validationErrors = new ArrayList<>();

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

    public List<ValidationException.ValidationError> getValidationErrors()
    {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationException.ValidationError> validationErrors)
    {
        this.validationErrors = validationErrors;
    }

}
