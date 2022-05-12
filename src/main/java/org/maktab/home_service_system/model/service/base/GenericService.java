package org.maktab.home_service_system.model.service.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//public interface GenericService<T,ID> {
public interface GenericService<R extends JpaRepository<T,ID>,T,ID> {
    T save(T t);
    T update(T t);
    void delete(T t);
    void deleteById(ID id);
    T findByID(ID id);
    List<T> findAll();

}
