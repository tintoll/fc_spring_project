package io.tintoll.eatgo.application;

public class EmailNotExistedException extends RuntimeException {

    public EmailNotExistedException() {
        super("Email is not existed");
    }
}
