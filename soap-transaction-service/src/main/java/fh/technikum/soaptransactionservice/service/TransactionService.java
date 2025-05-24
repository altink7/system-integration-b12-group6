package fh.technikum.soaptransactionservice.service;

import fh.technikum.soaptransactionservice.model.Transaction;
import fh.technikum.soaptransactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(Integer id, String name, Instant timestamp, BigDecimal amount) {
        transactionRepository.save(new Transaction(id, name, timestamp, amount));
    }
}
