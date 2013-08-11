package com.mfullen.rest.services;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author mfullen
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target(
{
    ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD
})
public @interface HostnameUrl
{
}
