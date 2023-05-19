package hu.alagi.logixspring.exception;

import java.util.List;

public class ValidationErrorEntity extends DefaultErrorEntity {
    private List<FieldErrorDto> fieldErrorList;

    public ValidationErrorEntity(ErrorCode errorCode, String errorMessage, List<FieldErrorDto> fieldErrorList) {
        super(errorCode, errorMessage);
        this.fieldErrorList = fieldErrorList;
    }

    public List<FieldErrorDto> getFieldErrorList() {
        return fieldErrorList;
    }

    public void setFieldErrorList(List<FieldErrorDto> fieldErrorList) {
        this.fieldErrorList = fieldErrorList;
    }
}
