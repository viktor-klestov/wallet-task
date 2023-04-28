package com.example.demo.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Balance can not be negative")
public class NegativeBalanceException extends RuntimeException {
}
