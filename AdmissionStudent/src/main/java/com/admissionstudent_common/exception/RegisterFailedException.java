package com.admissionstudent_common.exception;

import com.admissionstudent_common.constant.MessageConstant;

public class RegisterFailedException extends BaseException{
    public RegisterFailedException() {
        super(MessageConstant.SIGN_UP_FAILED);
    }

    public RegisterFailedException(String msg) {
        super(msg);
    }
}
