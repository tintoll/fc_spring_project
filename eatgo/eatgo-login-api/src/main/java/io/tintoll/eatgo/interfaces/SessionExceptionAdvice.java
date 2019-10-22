package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.application.EmailNotExistedException;
import io.tintoll.eatgo.application.PasswordWrongException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SessionExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordWrongException.class)
    @ResponseBody
    public String handlePasswordWrong() {
        return "{}";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailNotExistedException.class)
    @ResponseBody
    public String handleEmailNotExisted() {
        return "{}";
    }
}
