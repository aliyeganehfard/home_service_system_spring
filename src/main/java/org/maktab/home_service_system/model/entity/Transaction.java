package org.maktab.home_service_system.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.maktab.home_service_system.model.entity.base.BaseEntity;
import org.maktab.home_service_system.model.entity.base.Accounts;
import org.maktab.home_service_system.model.util.TransactionType;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Table(name = "transactions")
@Entity
public class Transaction extends BaseEntity<Integer> {
    private Date date;
//    @Temporal(TemporalType.TIMESTAMP)
    private Time time;
    private Long amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne
    private Accounts sender;

    @ManyToOne
    private Accounts receiver;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
