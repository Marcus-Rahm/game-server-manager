package dev.rahm.rabbithole.adapter.out.db;

import dev.rahm.rabbithole.domain.model.Server;
import dev.rahm.rabbithole.domain.model.ServerStatus;
import dev.rahm.rabbithole.domain.ports.out.ServerRepositoryPort;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PanacheServerRepositoryAdapter implements PanacheRepository<Server>, ServerRepositoryPort {
    @Override
    public Server save(Server server) {
        persist(server);
        return server;
    }

    @Override
    public Optional<Server> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }

    @Override
    public Optional<Server> findByName(String name) {
        return find("name", name).firstResultOptional();
    }

    @Override
    public List<Server> listAll() {
        return findAll().stream().toList();
    }

    @Override
    public List<Server> findByStatus(ServerStatus status) {
        return list("status", status);
    }

    @Override
    public void delete(Server server) {
        delete(server);
    }

    @Override
    public boolean existsByName(String name) {
        return count("name", name) > 0;
    }
}
