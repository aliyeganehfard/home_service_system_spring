package org.maktab.home_service_system.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.maktab.home_service_system.model.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Entity
public class Services extends BaseEntity<Integer> {
    @Column(unique = true , name = "service_name")
    private String name;

    @OneToMany(mappedBy = "services",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SubServices> subServices;

    @ManyToMany(mappedBy = "services",fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private Set<Expert> experts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Services services = (Services) o;
        return getId() != null && Objects.equals(getId(), services.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
