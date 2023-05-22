package com.nishanneupane.whatsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nishanneupane.whatsapp.modal.Chat;
import com.nishanneupane.whatsapp.modal.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
	
	@Query("select m from Message m join m.chat c where c.id=:chatId")
	List<Message> findByChatId(@Param("chatId") Integer chatId);

}
