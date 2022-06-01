package com.company.bankingapp.model.entity;

import com.company.bankingapp.model.enums.AccountStatus;
import com.company.bankingapp.model.enums.CurrencyType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class AccountEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account")
    @SequenceGenerator(name = "seq_account", sequenceName = "seq_account",
            allocationSize = 1000, initialValue = 1000)
    private Long id;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyType currencyType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_entity_id", nullable = false)
    private CustomerEntity customerEntity;

    @OneToMany(mappedBy = "accountEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<TransactionEntity> transactionEntities = new LinkedHashSet<>();

    public void addTransaction(TransactionEntity transactionEntity){
        this.transactionEntities.add(transactionEntity);
    }

}
