package com.example.demo.wallet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static com.example.demo.wallet.WalletService.MIN_BALANCE;

@Entity
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue(generator = "wallet_id_seq")
    @SequenceGenerator(name = "wallet_id_seq")
    private Long id;
    @Size(min = 3, max = 20)
    private String name;
    @Min(MIN_BALANCE)
    private BigDecimal balance = BigDecimal.ZERO;
}
