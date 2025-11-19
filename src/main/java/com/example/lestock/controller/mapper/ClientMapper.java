package com.example.lestock.controller.mapper;
import com.example.lestock.controller.dto.ClientDTO;
import com.example.lestock.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toClient(ClientDTO clientDTO);
    ClientDTO toClientDTO(Client client);
}
