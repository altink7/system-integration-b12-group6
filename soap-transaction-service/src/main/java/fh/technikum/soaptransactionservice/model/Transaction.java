package fh.technikum.soaptransactionservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer accountId;
    private String name;
    private Instant timestamp;
    private BigDecimal amount;

    public Transaction(Integer accountId, String name, Instant timestamp, BigDecimal amount) {
        this.accountId = accountId;
        this.name = name;
        this.timestamp = timestamp;
        this.amount = amount;
    }
}
