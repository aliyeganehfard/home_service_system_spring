package org.maktab.home_service_system.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.catalina.User;
import org.hibernate.Hibernate;
import org.maktab.home_service_system.model.entity.base.Accounts;
import org.maktab.home_service_system.model.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Entity
public class Credit extends BaseEntity<Integer> {

    private Long balance;

    @OneToOne
    private Accounts account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Credit credit = (Credit) o;
        return getId() != null && Objects.equals(getId(), credit.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
