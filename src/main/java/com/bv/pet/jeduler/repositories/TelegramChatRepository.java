package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.TelegramChat;
import com.bv.pet.jeduler.repositories.projections.chat.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelegramChatRepository extends JpaRepository<TelegramChat, Long> {
    List<UserChat> getAllBy();
}
