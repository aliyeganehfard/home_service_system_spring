package org.maktab.home_service_system.model.service.impl;

import org.maktab.home_service_system.model.entity.Credit;
import org.maktab.home_service_system.model.repository.CreditRepository;
import org.maktab.home_service_system.model.service.CreditService;
import org.maktab.home_service_system.model.service.base.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CreditServiceImpl extends GenericServiceImpl<CreditRepository,Credit,Integer> implements CreditService {

    public CreditServiceImpl(CreditRepository creditRepository) {
        super(creditRepository);
    }
}
