package fh.technikum.restaccountbalance.controller;

import fh.technikum.restaccountbalance.model.Transaction;
import fh.technikum.restaccountbalance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionRepository transactionRepository;

    @GetMapping
    public List<Transaction> getTransactions(
            @RequestParam("accountId") int accountId,
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to
    ) {
        if (from != null && to != null) {
            return transactionRepository.findByAccountIdAndTimestampBetween(accountId, from, to);
        } else {
            return transactionRepository.findByAccountId(accountId);
        }
    }
}