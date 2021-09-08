package br.com.hotel.utils;

import br.com.hotel.exception.NotFoundException;
import br.com.hotel.exception.ServiceException;

import java.security.InvalidParameterException;

public class ServiceExceptionBuilder {

    private ServiceExceptionBuilder() {}

    public static ServiceException getServiceException(final Throwable cause) {
        return new ServiceException(cause);
    }

    public static ServiceException throwNotFoundException(final Long id) {
        return getServiceException(new NotFoundException(id));
    }

    public static ServiceException throwInvalidParameterException(final String s) {
        return getServiceException(new InvalidParameterException(s));
    }

}
