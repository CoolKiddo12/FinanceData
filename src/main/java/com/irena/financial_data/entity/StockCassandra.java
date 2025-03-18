package com.irena.financial_data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("stock_prices")
public class StockCassandra {

    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @Column("symbol")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String symbol;

    @Column("timestamp")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant timestamp;

    @Column("open")
    private BigDecimal open;

    @Column("high")
    private BigDecimal high;

    @Column("low")
    private BigDecimal low;

    @Column("close")
    private BigDecimal close;

    @Column("volume")
    private Long volume;

}
