package com.example.demo.wallet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aWallet")
public class TestWalletBuilder {
    private Long id = 377L;
    private String name = "test wallet";
    private BigDecimal balance = BigDecimal.TEN;

    Wallet build() {
        Wallet built = new Wallet();
        built.setId(id);
        built.setName(name);
        built.setBalance(balance);
        return built;
    }
}
