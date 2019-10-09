package io.tintoll.eatgo.application;

public class EmailExistedException extends RuntimeException {

    public EmailExistedException(String email) {
        super("Email is Existed : "+email);
    }
}
