package com.bv.pet.jeduler.services.user;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.dtos.UserDto;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bv.pet.jeduler.entities.user.Role.USER;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ApplicationInfo applicationInfo;


    @Override
    @Transactional
    public void save(UserDto userDto) {
        userRepository.save(
                new User(
                        userDto.username(),
                        passwordEncoder.encode(userDto.password()),
                        USER
                )
        );

        applicationInfo.userAmount().increment();
    }

    @Override
    @Transactional
    public void delete(Short id) {
        userRepository.deleteById(id);
        applicationInfo.userAmount().decrement();
    }
}
