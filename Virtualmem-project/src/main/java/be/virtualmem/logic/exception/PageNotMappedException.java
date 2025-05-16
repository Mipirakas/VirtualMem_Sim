package be.virtualmem.logic.exception;

public class PageNotMappedException extends Exception {
    public PageNotMappedException() {
        super(ExceptionMessages.PAGE_NOT_MAPPED_EXCEPTION_MESSAGE);
    }
}
