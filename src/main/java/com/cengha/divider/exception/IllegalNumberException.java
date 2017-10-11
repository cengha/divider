package com.cengha.divider.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Illegal Number")
public class IllegalNumberException extends RuntimeException{

}
