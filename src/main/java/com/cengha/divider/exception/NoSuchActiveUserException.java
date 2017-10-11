package com.cengha.divider.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Active user is not found")
public class NoSuchActiveUserException extends RuntimeException{

}
