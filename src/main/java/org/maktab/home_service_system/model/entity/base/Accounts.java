package org.maktab.home_service_system.model.entity.base;

import jdk.jfr.Timespan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
//import org.maktab.home_service_system.model.entity.Credit;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.maktab.home_service_system.model.entity.Credit;
import org.maktab.home_service_system.model.util.UserState;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type" , discriminatorType = DiscriminatorType.STRING)
@Entity
public class Accounts extends BaseEntity<Integer> {
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private UserState userState;
    @CreationTimestamp
    private Time timeOfRegister;
    private Date dateOfRegister ;
    @Column(nullable = false, length = 8)
    private String password;
    @Column(unique = true)
    private String username;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Credit credit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Accounts user = (Accounts) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
