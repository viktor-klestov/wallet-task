package com.example.demo.wallet;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletDto {
    private long id;
    private String name;
    private BigDecimal balance;

    public WalletDto(Wallet model) {
        id = model.getId();
        name = model.getName();
        balance = model.getBalance();
    }
}
