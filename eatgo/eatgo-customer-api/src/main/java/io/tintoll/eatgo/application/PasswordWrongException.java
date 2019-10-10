package io.tintoll.eatgo.application;

public class PasswordWrongException extends RuntimeException {

    public PasswordWrongException() {
        super("Password is Wrong");
    }
}
