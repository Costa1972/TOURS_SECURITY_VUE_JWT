package ru.costa.tours_vue_jwt.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.costa.tours_vue_jwt.entity.*;
import ru.costa.tours_vue_jwt.repository.RoleRepository;
import ru.costa.tours_vue_jwt.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Init(UserRepository userRepository,
                RoleRepository roleRepository,
                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

        Role user = Role.builder()
                .roleName("ROLE_USER")
                .build();
        Role admin = Role.builder()
                .roleName("ROLE_ADMIN")
                .build();
        Set<Role> roles = new HashSet<>();
        roles.add(user);
        roles.add(admin);

//        Password password1 = Password.builder()
//                .password(passwordEncoder.encode("anton"))
//                .build();
//        Password password2 = Password.builder()
//                .password(passwordEncoder.encode("oleg"))
//                .build();

        Passport passport1 = Passport.builder()
                .series("4523")
                .number("439872")
                .build();
        Passport passport2 = Passport.builder()
                .series("4592")
                .number("391003")
                .build();

        Phone homePhone1 = Phone.builder().number("+7 495 934-23-11").build();
        Phone mobilePhone1 = Phone.builder().number("+7 916 923-91-00").build();
        Phone homePhone2 = Phone.builder().number("+7 812 234-967-20").build();
        Phone mobilePhone2 = Phone.builder().number("+7 916 128-73-09").build();
        Set<Phone> phones = new HashSet<>();
        phones.add(homePhone1);
        phones.add(mobilePhone1);
        phones.add(homePhone2);
        phones.add(mobilePhone2);

        Payment payment1 = Payment.builder().amount(15000.00).basisOfPayment(BasisOfPayment.BUS).build();
        Payment payment2 = Payment.builder().amount(12000.00).basisOfPayment(BasisOfPayment.MUSICIANS).build();
        Payment payment3 = Payment.builder().amount(7000.00).basisOfPayment(BasisOfPayment.MEAL).build();
        Payment payment4 = Payment.builder().amount(5000.00).basisOfPayment(BasisOfPayment.TRAIN).build();


        User user1 = User.builder()
                .lastName("Антонов")
                .firstName("Антон")
                .patronymic("Антонович")
                .username("anton@mail.ru")
                .phones(Set.of(homePhone1, mobilePhone1))
                .passport(passport1)
                .password(passwordEncoder.encode("anton"))
                .payments(Set.of(payment1, payment4))
                .roles(Set.of(admin))
                .build();
        User user2 = User.builder()
                .lastName("Смирнов")
                .firstName("Олег")
                .patronymic("Валерьевич")
                .username("oleg@mail.ru")
                .phones(Set.of(homePhone2, mobilePhone2))
                .passport(passport2)
                .password(passwordEncoder.encode("oleg"))
                .payments(Set.of(payment3, payment2))
                .roles(Set.of(user))
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
    }
}
