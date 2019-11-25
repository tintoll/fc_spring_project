package com.fc.project3.mycontact.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Martin
 * @since 30/09/2019
 */
@Slf4j
public class PersonNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Person Entity가 존재하지 않습니다";

    public PersonNotFoundException() {
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
