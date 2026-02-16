package com.oplink.server.repository;

import com.oplink.server.domain.ServerMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServerMembershipRepository extends JpaRepository<ServerMembership, Long> {
    List<ServerMembership> findByUserId(Long userId);
    List<ServerMembership> findByServerId(Long serverId);
    Optional<ServerMembership> findByServerIdAndUserId(Long serverId, Long userId);
    boolean existsByServerIdAndUserId(Long serverId, Long userId);
}
