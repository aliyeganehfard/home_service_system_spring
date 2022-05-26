package org.maktab.home_service_system.model.service.base;

import org.maktab.home_service_system.controller.dto.CustomerDto;
import org.maktab.home_service_system.controller.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class GenericServiceImpl<R extends JpaRepository<T,ID>,T, ID> implements GenericService<R,T, ID> {

    private final R r;
    public GenericServiceImpl(R r) {
        this.r = r;
    }


    @Override
    public T save(T t) {
        return r.save(t);
    }

    @Override
    public T update(T t) {
        return r.save(t);
    }

    @Override
    public void delete(T t) {
        r.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        r.deleteById(id);
    }

    @Override
    public T findByID(ID id) {
        return r.findById(id)
                .orElseThrow(()-> new NotFoundException(id.toString())
                );
    }

    @Override
    public List<T> findAll() {
        return r.findAll();
    }
}
