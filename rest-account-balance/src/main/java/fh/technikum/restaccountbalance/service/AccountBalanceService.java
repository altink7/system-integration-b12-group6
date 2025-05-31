package fh.technikum.restaccountbalance.service;

import fh.technikum.restaccountbalance.dto.AccountBalanceResponse;
import fh.technikum.restaccountbalance.model.Transaction;
import fh.technikum.restaccountbalance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AccountBalanceService {

    private final TransactionRepository transactionRepository;

    public AccountBalanceResponse calculateBalance(int id, Instant timestamp) {
        if (id <= 0) {
            throw new IllegalArgumentException("Account ID must be a positive integer.");
        }

        var transactions = transactionRepository.findByAccountIdAndTimestampLessThanEqual(id, getTimestamp(timestamp));

        BigDecimal total = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String name = transactions.stream().findFirst()
                .map(Transaction::getName)
                .orElse("Unknown");

        return new AccountBalanceResponse(id, name, total);
    }

    private Instant getTimestamp(Instant timestamp) {
        return timestamp != null ? timestamp : Instant.now();
    }
}
