package org.maktab.home_service_system.model.service.impl;

import org.maktab.home_service_system.controller.dto.CreditDto;
import org.maktab.home_service_system.controller.exception.CreditNotFoundException;
import org.maktab.home_service_system.controller.exception.NotFoundException;
import org.maktab.home_service_system.model.entity.Credit;
import org.maktab.home_service_system.model.entity.base.Accounts;
import org.maktab.home_service_system.model.repository.CreditRepository;
import org.maktab.home_service_system.model.service.CreditService;
import org.maktab.home_service_system.model.service.base.GenericServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditServiceImpl extends GenericServiceImpl<CreditRepository,Credit,Integer> implements CreditService {
    private final CreditRepository creditRepository;
    private final ModelMapper modelMapper;
    private final ExpertServiceImpl expertService;
    private final CustomerServiceImpl customerService;
    public CreditServiceImpl(CreditRepository creditRepository,ModelMapper modelMapper,
                             ExpertServiceImpl expertService, CustomerServiceImpl customerService) {
        super(creditRepository);
        this.creditRepository = creditRepository;
        this.modelMapper = modelMapper;
        this.expertService = expertService;
        this.customerService = customerService;
    }

    public CreditDto save(CreditDto creditDto){
        Accounts account;
        try {
            account = expertService.findExpertById(creditDto.getAccountId());
        }catch (Exception e){
            account = customerService.findCustomerById(creditDto.getAccountId());
        }


        extracted( account);

        var credit = modelMapper.map(creditDto,Credit.class);
        return modelMapper.map(creditRepository.save(credit),CreditDto.class);
    }

    private void extracted( Accounts account) {
        if (account == null)
            throw new NotFoundException();
    }

    public CreditDto findById(Integer id){
        return modelMapper.map(findCreditById(id),CreditDto.class);
    }

    protected Credit findCreditById(Integer id) {
        return creditRepository.findById(id)
                .orElseThrow(() -> new CreditNotFoundException(id.toString()));
    }


    public List<CreditDto> findAllCredit(){
        return creditRepository.findAll().stream()
                .map(credit -> modelMapper.map(credit,CreditDto.class))
                .collect(Collectors.toList());
    }

    public CreditDto findByAccountId(Integer accountId){
        return modelMapper.map(creditRepository.findCreditByAccountId(accountId),CreditDto.class);
    }
}
