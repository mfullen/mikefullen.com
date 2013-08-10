package com.mfullen.rest.services;

import com.mfullen.rest.exceptions.ValidationException;
import java.util.Set;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 *
 * @author mfullen
 */
public abstract class AbstractService
{
    @Inject
    private Validator validator;

    protected void validate(Object request)
    {
        Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(request);

        if (constraintViolations.size() > 0)
        {
            throw new ValidationException(constraintViolations);
        }
    }
}
