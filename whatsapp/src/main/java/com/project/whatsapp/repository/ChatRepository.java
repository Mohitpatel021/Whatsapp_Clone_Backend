package com.project.whatsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.whatsapp.modal.Chat;
import com.project.whatsapp.modal.User;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
@Query(value="select c from Chat c join c.users u where u.id=?1")
	public List<Chat> findChatByUserId(@Param("userId") Integer userId);

	@Query(value = "select c from Chat c where c.isGroup=false and ?1 Member of c.users and ?2 Member of c.users")
	public Chat findSingleChatByUserIds(@Param("user") User user, @Param("regUser") User regUser);
}
