package com.oplink.server.repository;

import com.oplink.server.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    List<Channel> findByServerIdOrderByPositionAsc(Long serverId);
}
