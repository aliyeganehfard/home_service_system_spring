package org.maktab.home_service_system.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.maktab.home_service_system.model.entity.base.BaseEntity;
import org.maktab.home_service_system.model.util.OrderState;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Integer> {

    private Long price;
    private String description;
    private Date dateOfRegistration;
    private Date dateOfWork;
    private Time timeOfWork;
    private String address;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private SubServices subServices;

    @OneToMany(mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Offer> offer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
