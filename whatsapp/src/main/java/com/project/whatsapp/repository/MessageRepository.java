package com.project.whatsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.whatsapp.modal.Message;
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("Select m from Message m join m.chat c where c.id=?1")
	public List<Message> findByChatId(@Param("chatId") Integer chatId);
}
