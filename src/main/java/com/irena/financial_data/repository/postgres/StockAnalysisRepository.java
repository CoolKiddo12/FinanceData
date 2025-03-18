package com.irena.financial_data.repository.postgres;

import com.irena.financial_data.entity.StockAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockAnalysisRepository extends JpaRepository<StockAnalysis, Long> {
    StockAnalysis findBySymbol(String symbol);
}
