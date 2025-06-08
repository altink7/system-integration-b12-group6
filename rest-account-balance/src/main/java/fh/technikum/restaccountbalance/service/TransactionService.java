package fh.technikum.restaccountbalance.service;

import fh.technikum.restaccountbalance.model.Transaction;
import fh.technikum.restaccountbalance.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> findByAccountIdAndTimestampBetween(int accountId, Instant from, Instant to) {
        if (from != null && to != null) {
            return transactionRepository.findByAccountIdAndTimestampBetween(accountId, from, to);
        } else {
            return transactionRepository.findByAccountId(accountId);
        }
    }
}
