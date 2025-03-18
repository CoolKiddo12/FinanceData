package com.irena.financial_data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "stock_analysis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private BigDecimal sma;               // Simple Moving Average
    private BigDecimal ema;               // Exponential Moving Average
    private BigDecimal rsi;               // Relative Strength Index
    private BigDecimal macd;              // Moving Average Convergence Divergence
    private BigDecimal stochasticOscillator; // Stochastic Oscillator

    @Column(name = "timestamp")
    private LocalDate timestamp;
}

