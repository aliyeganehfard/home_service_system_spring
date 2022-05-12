package org.maktab.home_service_system;

import org.maktab.home_service_system.model.entity.*;
import org.maktab.home_service_system.model.service.impl.*;
import org.maktab.home_service_system.model.util.OrderState;
import org.maktab.home_service_system.model.util.Score;
import org.maktab.home_service_system.model.util.UserState;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class HomeServiceSystemApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(HomeServiceSystemApplication.class, args);
    }

    @Component
    static class onStartUp implements ApplicationRunner {

        private final AdminServiceImpl adminService;
        private final CustomerServiceImpl customerService;
        private final ExpertServiceImpl expertService;
        private final CreditServiceImpl creditService;
        private final CommentsServiceImpl commentsService;
        private final OfferServiceImpl offerService;
        private final OrderServiceImpl orderService;
        private final ServicesServiceImpl servicesService;
        private final SubServicesServiceImpl subServicesService;
        private final TransactionServiceImpl transactionService;

        public onStartUp(AdminServiceImpl adminService, CustomerServiceImpl customerService, CommentsServiceImpl commentsService,
                         ExpertServiceImpl expertService, CreditServiceImpl creditService, OfferServiceImpl offerService, TransactionServiceImpl transactionService,
                         OrderServiceImpl orderService, ServicesServiceImpl servicesService, SubServicesServiceImpl subServicesService) {
            this.adminService = adminService;
            this.customerService = customerService;
            this.expertService = expertService;
            this.creditService = creditService;
            this.commentsService = commentsService;
            this.offerService = offerService;
            this.orderService = orderService;
            this.servicesService = servicesService;
            this.subServicesService = subServicesService;
            this.transactionService = transactionService;
        }

        @Override
        public void run(ApplicationArguments args) {
            var admin = Admin.builder().firstname("a").lastname("B")
                    .password("123456as").email("a@gmail.com").username("aadsfdmin")
                    .build();
            var experts = List.of(
                    Expert.builder()
                            .password("1234567d").firstname("abc").lastname("C").username("abac").email("abd@gmail.com").services(new HashSet<>())
                            .build(),
                    Expert.builder()
                            .password("1e34567d").firstname("abc").lastname("C").username("cbea").email("cba@gmail.com").services(new HashSet<>())
                            .build()
            );

            var customers = Arrays.asList(
                    Customer.builder()
                            .firstname("c").lastname("cc").email("cc").password("1234567a").username("imc")
                            .build(),

                    Customer.builder()
                            .firstname("d").lastname("dd").email("dd").password("dd123456").username("imd")
                            .build(),

                    Customer.builder()
                            .firstname("e").lastname("ee").email("ee").password("ee123456").username("ime")
                            .build()
            );

            var orders = Arrays.asList(
                    Order.builder()
                            .customer(customers.get(0)).dateOfWork(Date.valueOf("2023-12-12")).description("description of order")
                            .address("came my home").price(12000L).timeOfWork(Time.valueOf("20:20:20"))
                            .build(),
                    Order.builder()
                            .customer(customers.get(1)).dateOfWork(Date.valueOf("2023-12-12")).description("description of new order")
                            .address("tehran .... my home").price(12000L).timeOfWork(Time.valueOf("20:20:20"))
                            .build()
            );

            var offers = Arrays.asList(
                    Offer.builder()
                            .suggestedPrice(15000L).order(orders.get(0)).expert(experts.get(0)).durationOfWork(3)
                            .build(),
                    Offer.builder()
                            .suggestedPrice(25000L).order(orders.get(0)).expert(experts.get(1)).durationOfWork(2)
                            .build()
            );

            var services = Arrays.asList(
                    Services.builder()
                            .name("a").subServices(new HashSet<>()).experts(new HashSet<>())
                            .build(),
                    Services.builder()
                            .name("b").subServices(new HashSet<>()).experts(new HashSet<>())
                            .build()
            );

            var subServices = Arrays.asList(
                    SubServices.builder()
                            .name("subA").services(services.get(0))
                            .build(),
                    SubServices.builder()
                            .name("subB").services(services.get(0))
                            .build(),
                    SubServices.builder()
                            .name("subC").services(services.get(1))
                            .build()
            );

            var comments = List.of(
                    Comments.builder()
                            .customer(orders.get(0).getCustomer()).expert(experts.get(0))
                            .comment("good").score(Score.FIVE).build()
            );
           var credits = List.of(
                    Credit.builder()
                            .balance(105000L).account(customers.get(0))
                            .build(),
                    Credit.builder()
                            .balance(10000L).account(experts.get(0))
                            .build()
            );
//            save
            System.out.println(adminService.save(admin));
            customers.forEach(customer -> System.out.println(customerService.save(customer)));
            experts.forEach(expert -> System.out.println(expertService.save(expert)));
            services.forEach(service -> System.out.println(servicesService.save(service)));
            subServices.forEach(subService -> System.out.println(subServicesService.save(subService)));
            orders.forEach(order -> System.out.println(orderService.save(order)));
            offers.forEach(offer -> System.out.println(offerService.save(offer)));
            System.out.println(commentsService.save(comments.get(0)));
            credits.forEach(credit -> System.out.println(creditService.save(credit)));

//            admin crud and feature
            admin.setEmail("update@email.com");
            System.out.println(adminService.update(admin));

            System.out.println(adminService.findByID(admin.getId()));

            adminService.findAll().forEach(System.out::println);

            System.out.println(adminService.login(admin.getUsername(), admin.getPassword()));

            adminService.setExpertState(experts.get(0), UserState.CONFIRMED);
            System.out.println(expertService.findByID(experts.get(0).getId()));

            System.out.println(adminService.changePassword(admin, admin.getPassword(), "newAdmin", "789d543"));


//            customer crud and feature
            customers.get(0).setEmail("updatde@email.com");
            System.out.println(customerService.update(customers.get(0)));

            System.out.println(customerService.findByID(customers.get(0).getId()));

            System.out.println(customerService.findAll());

            System.out.println(customerService.changePassword(customers.get(1), customers.get(0).getPassword(), "newUsername", "pass4321"));

            System.out.println(customerService.login(customers.get(0).getUsername(), customers.get(0).getPassword()));

            System.out.println(customerService.search(customers.get(0).getFirstname(), null, null));


//            expert crud and feature
            experts.get(0).setEmail("update@emaisdfl.com");
            System.out.println(expertService.update(experts.get(0)));

            System.out.println(expertService.findByID(experts.get(0).getId()));

            System.out.println(expertService.findAll());

            experts.get(0).setUserState(UserState.NEW);
            expertService.addExpertToService(experts.get(0), services.get(0));

            experts.get(1).setUserState(UserState.NEW);
            expertService.addExpertToService(experts.get(1), services.get(0));

            experts.get(0).setUserState(UserState.NEW);
            System.out.println(expertService.deleteExpertOfService(experts.get(0), services.get(0)));

            System.out.println(expertService.findByUserState(UserState.NEW));

            System.out.println(expertService.login(experts.get(0).getUsername(), experts.get(0).getPassword()));

            System.out.println(expertService.changePassword(experts.get(0), experts.get(0).getPassword(),"newUfdfhsername", "123s5678"));

            System.out.println(expertService.search(experts.get(0).getFirstname(), null, null));


//            services crud and feature
            services.get(0).setName("test");
            System.out.println(servicesService.update(services.get(0)));

            System.out.println(servicesService.findByID(services.get(0).getId()));

            System.out.println(servicesService.findAll());


//            subServices crud and feature
            subServices.get(1).setName("newName");
            System.out.println(subServicesService.update(subServices.get(1)));

            System.out.println(subServicesService.findByID(subServices.get(0).getId()));

            System.out.println(subServicesService.findAll());


//            order crud and feature
            orders.get(0).setPrice(15150L);
            System.out.println(orderService.update(orders.get(0)));

            System.out.println(orderService.findByID(orders.get(0).getId()));

            System.out.println(orderService.findAll());

            System.out.println(orderService.findByOrderState(OrderState.WAITING_FOR_EXPERT_SUGGESTION));

            System.out.println(orderService.selectAnExpertToOrder(orders.get(0),offers.get(0)));

            System.out.println(orderService.changeOrderState(orders.get(1), OrderState.DONE));


//            offer crud and feature
            offers.get(0).setSuggestedPrice(1000L);
            offerService.update(offers.get(0));

            System.out.println(offerService.findByID(offers.get(0).getId()));

            System.out.println(offerService.findAll());

            System.out.println(offerService.findOrderOffers(orders.get(0).getId()));


//            comments crud and feature
            comments.get(0).setComment("so good");
            System.out.println(commentsService.update(comments.get(0)));

            System.out.println(commentsService.findByID(comments.get(0).getId()));

            System.out.println(commentsService.findAll());


//            credit and transaction crud and feature
            credits.get(0).setBalance(100005L);
            System.out.println(creditService.update(credits.get(0)));

            System.out.println(creditService.findByID(credits.get(0).getId()));

            System.out.println(creditService.findAll());

            var c = customerService.findByID(customers.get(0).getId());
            var e = expertService.findByID(experts.get(0).getId());
            var o = orderService.findByID(orders.get(0).getId());
            System.out.println(transactionService.paymentMoney(c, e, o));

            System.out.println(transactionService.charge(c, 50000L));

            System.out.println(transactionService.withdraw(e,100L));
        }
    }
}
