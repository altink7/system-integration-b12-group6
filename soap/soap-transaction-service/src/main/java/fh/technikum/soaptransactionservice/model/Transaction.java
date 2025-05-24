package fh.technikum.soaptransactionservice.model;

import jakarta.persistence.Entity;
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
    private Integer id;
    private String name;
    private Instant timestamp;
    private BigDecimal amount;
}
