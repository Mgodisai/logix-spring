package hu.alagi.logixspring.exception;

public enum ErrorCode {
    DEFAULT(1000),
    VALIDATION(1001),
    ENTITY_NOT_EXISTS(1002),
    ENTITY_ALREADY_EXISTS(1003),
    ENTITY_ID_MISMATCH(1004),
    INVALID_COUNTRY_CODE(1005),
    ILLEGAL_ARGUMENT(1006);

    private final int internalErrorCode;

    ErrorCode(int errorCode) {
        this.internalErrorCode = errorCode;
    }

    public int getInternalErrorCode() {
        return this.internalErrorCode;
    }
}
