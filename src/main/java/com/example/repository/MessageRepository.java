package com.example.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("FROM Message WHERE message_id = :messageId")
    Message findMessageById(@Param("messageId") Integer messageId);

    @Query("FROM Message WHERE posted_by = :account_id")
    List<Message> findByPostedBy(@Param("account_id") Integer account_id);
}
