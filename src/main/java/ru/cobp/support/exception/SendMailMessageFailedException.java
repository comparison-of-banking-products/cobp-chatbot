package ru.cobp.support.exception;

public class SendMailMessageFailedException extends RuntimeException {

    public SendMailMessageFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
