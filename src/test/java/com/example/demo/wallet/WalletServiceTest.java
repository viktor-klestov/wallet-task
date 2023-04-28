package com.example.demo.wallet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {
    WalletService serviceUnderTest;
    @Mock
    WalletRepository mockRepository;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new WalletService(mockRepository);
    }

    @Test
    void shouldBeOrderedById() {
        Wallet first = TestWalletBuilder.aWallet().withId(1L).build();
        Wallet second = TestWalletBuilder.aWallet().withId(2L).build();
        Wallet third = TestWalletBuilder.aWallet().withId(3L).build();
        when(mockRepository.findAll()).thenReturn(List.of(second, third, first));

        List<Long> ids = serviceUnderTest.all().stream().map(WalletDto::getId).toList();

        assertThat(ids).containsExactly(1L, 2L, 3L);
    }

    @Test
    void shouldCallRepository() {
        when(mockRepository.findAll()).thenReturn(emptyList());

        Collection<WalletDto> all = serviceUnderTest.all();

        assertThat(all).isEmpty();
        verify(mockRepository).findAll();
    }

    @Test
    void shouldModify_whenWithdrawn() {
        Wallet existed = TestWalletBuilder.aWallet().build();
        when(mockRepository.findById(existed.getId())).thenReturn(Optional.of(existed));
        BigDecimal four = BigDecimal.valueOf(4);

        WalletDto modified = serviceUnderTest.withdraw(existed.getId(), four);

        BigDecimal six = BigDecimal.valueOf(6);
        assertThat(modified.getBalance()).isEqualTo(six);
    }

    @Test
    void shouldModify_whenDeposit() {
        Wallet existed = TestWalletBuilder.aWallet().build();
        when(mockRepository.findById(existed.getId())).thenReturn(Optional.of(existed));
        BigDecimal four = BigDecimal.valueOf(4);

        WalletDto modified = serviceUnderTest.deposit(existed.getId(), four);

        BigDecimal fourteen = BigDecimal.valueOf(14);
        assertThat(modified.getBalance()).isEqualTo(fourteen);
    }

    @Test
    void shouldThrowException_whenGoesBelowZero() {
        Wallet existed = TestWalletBuilder.aWallet().build();
        when(mockRepository.findById(existed.getId())).thenReturn(Optional.of(existed));
        BigDecimal eleven = BigDecimal.valueOf(11);

        assertThrows(NegativeBalanceException.class, () -> serviceUnderTest.withdraw(existed.getId(), eleven));
    }

    @Test
    void shouldThrowException_whenNegativeAmountWithdrawn() {
        BigDecimal minusThree = BigDecimal.valueOf(-3);

        assertThrows(NegativeAmountException.class, () -> serviceUnderTest.withdraw(123L, minusThree));
    }

    @Test
    void shouldThrowException_whenNegativeAmountDeposited() {
        BigDecimal minusThree = BigDecimal.valueOf(-3);

        assertThrows(NegativeAmountException.class, () -> serviceUnderTest.deposit(123L, minusThree));
    }

    @Test
    void shouldCreateNewWallet() {
        String testName = "test wallet";
        when(mockRepository.save(any())).thenReturn(TestWalletBuilder.aWallet().build());

        WalletDto created = serviceUnderTest.create(testName);

        assertThat(created.getName()).isEqualTo(testName);
        verify(mockRepository).save(any());
    }
}