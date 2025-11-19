package com.example.lestock.controller;
import com.example.lestock.controller.dto.ClientDTO;
import com.example.lestock.controller.mapper.ClientMapper;
import com.example.lestock.model.Client;
import com.example.lestock.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
public class ClientController implements GenericController{
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    public ResponseEntity<Void> saveClient(@RequestBody ClientDTO clientDTO){
        Client client = clientMapper.toClient(clientDTO);
        clientService.saveClient(client);
        URI location = generateHeaderLocation(client.getId());
        return ResponseEntity.created(location).build();
    }
}
