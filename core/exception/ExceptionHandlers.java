package com.etiya.ecommerce.core.exception;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.exception.types.ServiceException;
import com.etiya.ecommerce.core.utils.result.ErrorDataResult;
import com.etiya.ecommerce.core.utils.result.ErrorResult;
import com.etiya.ecommerce.core.utils.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler({ServiceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleBusinessException(ServiceException exception) {

        return new ErrorResult(exception.getMessage());
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exception) {

        Map<String, String> validationErrors = new HashMap<>();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {

            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ErrorDataResult<Object>errors=new ErrorDataResult<>(validationErrors);
        return errors;

    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleNotFound(NotFoundException exception) {
        return new ErrorResult(exception.getMessage());
    }
}
