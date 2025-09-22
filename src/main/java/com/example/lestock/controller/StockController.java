package com.example.lestock.controller;
import com.example.lestock.controller.dto.GetStockDTO;
import com.example.lestock.controller.dto.SaveStockDTO;
import com.example.lestock.controller.mapper.StockMapper;
import com.example.lestock.model.Stock;
import com.example.lestock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/{stock}")
@RequiredArgsConstructor
public class StockController implements GenericController {
    private final StockService stockService;
    private final StockMapper stockMapper;

    @PostMapping
    ResponseEntity<Void> saveStock(@RequestBody SaveStockDTO stockDTO) {
        Stock stockEntity = stockMapper.toEntity(stockDTO);
        stockService.saveStock(stockEntity);
        URI location = generateHeaderLocation(stockEntity.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    ResponseEntity<List<GetStockDTO>> getAllStocks() {
        List<GetStockDTO> stockDTOS = stockService.getAllStocks()
                .stream()
                .map(stockMapper::toDTO)
                .toList();
        return ResponseEntity.ok(stockDTOS);
    }


}
