package com.example.lestock.service;
import com.example.lestock.dao.StockDAO;
import com.example.lestock.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockDAO stockDAO;

    public void saveStock(Stock stock) {
        stockDAO.save(stock);
    }
}
