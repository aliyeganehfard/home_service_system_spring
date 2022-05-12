package org.maktab.home_service_system.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.maktab.home_service_system.model.entity.base.Accounts;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Entity
public class Expert extends Accounts {


    @Lob
    private Blob photo;

    private Integer score;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "expert_Services",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "services_id")
    )
    private Set<Services> services;

    @OneToMany(mappedBy = "expert",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Offer> suggestions;

    @OneToMany(mappedBy = "expert",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Comments> comments;

    @Override
    public String toString() {
        return "Expert{" +
                "score=" + super.getUsername() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Expert expert = (Expert) o;
        return getId() != null && Objects.equals(getId(), expert.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
