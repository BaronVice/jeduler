package com.bv.pet.jeduler.services.user;

import com.bv.pet.jeduler.application.cache.TelegramInfo;
import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.dtos.UserDto;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.bv.pet.jeduler.entities.user.Role.USER;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ApplicationInfo applicationInfo;
    private final TelegramInfo telegramInfo;

    @Transactional
    public short getIdOrElseCreateAndGet(String uuid){
        Optional<User> user = userRepository.findByUuid(uuid);
        return (user.isPresent() ? user.get().getId() : create(uuid).getId());
    }

    private User create(String uuid) {
        User user = new User(
                uuid,
                USER
        );

        userRepository.save(user);
        applicationInfo.addUser(user.getId());

        return user;
    }

    @Transactional
    public void delete(Short id) {
        userRepository.deleteById(id);
        applicationInfo.deleteUser(id);
        telegramInfo.getUsersChatId().remove(id);
        telegramInfo.getTokenHolder().values().remove(id);
    }
}
