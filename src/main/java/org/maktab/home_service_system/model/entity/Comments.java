package org.maktab.home_service_system.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.maktab.home_service_system.model.entity.base.BaseEntity;
import org.maktab.home_service_system.model.util.Score;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Entity
public class Comments extends BaseEntity<Integer> {

    private String comment;

    @Enumerated(EnumType.STRING)
    private Score score;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Expert expert;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comments comments = (Comments) o;
        return getId() != null && Objects.equals(getId(), comments.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
