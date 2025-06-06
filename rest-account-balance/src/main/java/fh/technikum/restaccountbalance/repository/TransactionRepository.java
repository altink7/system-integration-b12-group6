package fh.technikum.restaccountbalance.repository;

import fh.technikum.restaccountbalance.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdAndTimestampLessThanEqual(int id, Instant timestamp);

    List<Transaction> findByAccountId(int accountId);
    List<Transaction> findByAccountIdAndTimestampBetween(int accountId, Instant from, Instant to);
}
