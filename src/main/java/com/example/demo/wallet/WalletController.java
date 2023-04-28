package com.example.demo.wallet;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("wallets")
public class WalletController {
    private final WalletService service;

    @GetMapping
    public List<WalletDto> all() {
        return service.all();
    }

    @PostMapping
    public WalletDto create(@RequestBody String name) {
        return service.create(name);
    }

    @PostMapping("{id}/withdrawal")
    public WalletDto withdraw(@PathVariable("id") Long walletId, @RequestBody BigDecimal amount) {
        return service.withdraw(walletId, amount);
    }

    @PostMapping("{id}/deposit")
    public WalletDto deposit(@PathVariable("id") Long walletId, @RequestBody BigDecimal amount) {
        return service.deposit(walletId, amount);
    }
}
