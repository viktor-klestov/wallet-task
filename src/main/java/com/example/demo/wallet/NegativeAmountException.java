package com.example.demo.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Amount can not be negative")
public class NegativeAmountException extends RuntimeException {
}
