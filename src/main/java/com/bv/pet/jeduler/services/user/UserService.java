package com.bv.pet.jeduler.services.user;

import com.bv.pet.jeduler.datacarriers.dtos.UserDto;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.bv.pet.jeduler.entities.user.Role.USER;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void save(UserDto userDto) {
        userRepository.save(
                new User(
                        userDto.username(),
                        passwordEncoder.encode(userDto.password()),
                        USER
                )
        );
    }
}
