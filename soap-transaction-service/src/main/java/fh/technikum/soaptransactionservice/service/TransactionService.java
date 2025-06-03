package fh.technikum.soaptransactionservice.service;

import fh.technikum.soaptransactionservice.model.Transaction;
import fh.technikum.soaptransactionservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@RequiredArgsConstructor
@Transactional
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public void saveTransaction(Integer id, String name, Instant timestamp, BigDecimal amount) {
        if (id != null && name != null && amount != null) {
            transactionRepository.save(new Transaction(id, name, timestamp, amount));
        } else {
            throw new IllegalArgumentException("All fields (id, name, amount) must be provided.");
        }
    }
}
