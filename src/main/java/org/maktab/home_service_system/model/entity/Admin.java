package org.maktab.home_service_system.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.maktab.home_service_system.model.entity.base.Accounts;

import javax.persistence.Entity;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@Entity
public class Admin extends Accounts {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Admin admin = (Admin) o;
        return getId() != null && Objects.equals(getId(), admin.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
