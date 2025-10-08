package com.example.lestock.dao;
import com.example.lestock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientDAO extends JpaRepository<Client, Long> {
    Optional<Client> findByClientId(String clientId);
}
