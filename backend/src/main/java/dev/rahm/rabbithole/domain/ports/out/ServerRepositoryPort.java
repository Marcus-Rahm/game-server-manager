package dev.rahm.rabbithole.domain.ports.out;

import dev.rahm.rabbithole.domain.model.Server;
import dev.rahm.rabbithole.domain.model.ServerStatus;

import java.util.List;
import java.util.Optional;

/**
 * Driven Port (Outbound Port) for persistent storage of Server entities.
 * The application core depends on this interface.
 */
public interface ServerRepositoryPort {
    Server save(Server server);
    Optional<Server> findByIdOptional(Long id);
    Optional<Server> findByName(String name); // Useful for finding by human-readable name
    List<Server> listAll();
    List<Server> findByStatus(ServerStatus status);
    void delete(Server server);
    boolean existsByName(String name);
}
