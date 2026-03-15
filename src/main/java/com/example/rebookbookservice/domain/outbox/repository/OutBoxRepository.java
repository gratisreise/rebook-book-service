package com.example.rebookbookservice.domain.outbox.repository;

import com.example.rebookbookservice.domain.outbox.model.entity.Outbox;
import com.example.rebookbookservice.common.enums.MessageStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutBoxRepository extends JpaRepository<Outbox, Long> {

    List<Outbox> findTop20ByStatusOrderByCreatedAtAsc(MessageStatus messageStatus);
}
