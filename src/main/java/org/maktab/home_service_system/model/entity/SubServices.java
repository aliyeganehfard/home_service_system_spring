package org.maktab.home_service_system.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.maktab.home_service_system.model.entity.base.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Entity
public class SubServices extends BaseEntity<Integer> {
    @Column(unique = true)
    private String name;

    private Long basePrice;
    private String description;

    @ManyToOne
    private Services services;

    @Override
    public String toString() {
        return "SubServices{" +
                "name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", description='" + description + '\'' +
                ", services=" + services +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SubServices that = (SubServices) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
