package com.example.lestock.service;
import com.example.lestock.dao.ClientDAO;
import com.example.lestock.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientDAO clientDAO;
    private final PasswordEncoder passwordEncoder;

    public void saveClient(Client client){
        client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
        clientDAO.save(client);
    }

    public Optional<Client> getClientByClientID(String clientId){
        return clientDAO.findByClientId(clientId);
    }
}
