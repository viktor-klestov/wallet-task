package com.example.demo.wallet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class WalletService {
    public static final long MIN_BALANCE = 0;
    private final WalletRepository repository;

    public List<WalletDto> all() {
        return repository.findAll()
                .stream()
                .sorted(comparing(Wallet::getId))
                .map(WalletDto::new)
                .toList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public WalletDto withdraw(Long walletId, BigDecimal amount) {
        nonNegative(amount);
        return repository.findById(walletId)
                .map(wallet -> {
                    BigDecimal modifiedAmount = wallet.getBalance().subtract(amount);
                    if (modifiedAmount.compareTo(BigDecimal.valueOf(MIN_BALANCE)) < 0) {
                        throw new NegativeBalanceException();
                    }
                    wallet.setBalance(modifiedAmount);
                    return wallet;
                })
                .map(WalletDto::new)
                .get();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public WalletDto deposit(Long walletId, BigDecimal amount) {
        nonNegative(amount);
        return repository.findById(walletId)
                .map(wallet -> {
                    BigDecimal modifiedAmount = wallet.getBalance().add(amount);
                    wallet.setBalance(modifiedAmount);
                    return wallet;
                })
                .map(WalletDto::new)
                .get();
    }

    public WalletDto create(String name) {
        Wallet created = new Wallet();
        created.setName(name);
        return new WalletDto(repository.save(created));
    }

    private void nonNegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountException();
        }
    }
}