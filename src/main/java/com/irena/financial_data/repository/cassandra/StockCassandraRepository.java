package com.irena.financial_data.repository.cassandra;

import com.irena.financial_data.entity.StockCassandra;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface StockCassandraRepository extends CassandraRepository<StockCassandra, UUID> {

    // Find all stock data by symbol
    @Query(allowFiltering = true)
    List<StockCassandra> findBySymbol(String symbol);

    @Query("SELECT * FROM stock_prices WHERE symbol = ?0 AND timestamp >= ?1 AND timestamp < ?2 ALLOW FILTERING")
    List<StockCassandra> findStockDataForTimeRange(String symbol, Instant startOfDay, Instant endOfDay);

    @Query("SELECT * FROM stock_prices WHERE symbol = ?0 AND timestamp >= ?1 AND timestamp <= ?2 ALLOW FILTERING")
    List<StockCassandra> findStockPricesBySymbolAndTimestampRange(String symbol, Instant startTimestamp, Instant endTimestamp);
}
