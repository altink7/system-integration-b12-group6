package fh.technikum.soaptransactionservice.service;

import fh.technikum.soaptransactionservice.model.Transaction;
import fh.technikum.soaptransactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class TransactionService {

    private final TransactionRepository repo;

    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    public void saveTransaction(Integer id, String name, Instant timestamp, BigDecimal amount) {
        repo.save(new Transaction(id, name, timestamp, amount));
    }
}
