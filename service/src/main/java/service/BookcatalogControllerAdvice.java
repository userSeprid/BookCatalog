package service;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookcatalogControllerAdvice {

    @ResponseBody
    @ExceptionHandler(BookNotFoundExpeption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors BookNotFoundExceptionHandler(BookNotFoundExpeption ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
