package fh.technikum.soaptransactionservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
public class Transaction {

    @Id
    private Integer id;
    private String name;
    private Instant timestamp;
    private BigDecimal amount;

    public Transaction() {
    }

    public Transaction(Integer id, String name, Instant timestamp, BigDecimal amount) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.amount = amount;
    }
}
