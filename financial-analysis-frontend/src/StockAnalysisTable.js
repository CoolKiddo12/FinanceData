import React, { useEffect, useState } from "react";
import axios from "axios";
import './index.css';

const StockTable = () => {
    const [stockData, setStockData] = useState([]);
    const [filteredData, setFilteredData] = useState([]); // State for filtered data
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchStockData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/stockanalysis/all');
                console.log('Fetched stock data:', response.data); // Log the response

                setStockData(response.data); // Store all data
                setFilteredData(response.data); // Initially show all data
            } catch (error) {
                console.error('Error fetching stock data:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchStockData();
    }, []); // Empty dependency array, so it runs once when the component mounts

    const filterBySymbol = (symbol) => {
        const filtered = stockData.filter(stock => stock.symbol === symbol);
        setFilteredData(filtered); // Show only the selected symbol's data
    };

    const showAllData = () => {
        setFilteredData(stockData); // Show all data again
    };

    const symbols = [...new Set(stockData.map(stock => stock.symbol))]; // Get unique symbols

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            {/* Buttons for each symbol */}
            <div>
                {symbols.map(symbol => (
                    <button key={symbol} onClick={() => filterBySymbol(symbol)}>
                        {symbol}
                    </button>
                ))}
            </div>

            {/* Reset button to show all data */}
            <button onClick={showAllData}>Show All Data</button>

            {/* Table with filtered data */}
            <table>
                <thead>
                <tr>
                    <th>Symbol</th>
                    <th>SMA</th>
                    <th>EMA</th>
                    <th>RSI</th>
                    <th>MACD</th>
                    <th>Stochastic Oscillator</th>
                    <th>Timestamp</th>
                </tr>
                </thead>
                <tbody>
                {filteredData.map((stock) => (
                    <tr key={stock.id}>
                        <td>{stock.symbol}</td>
                        <td>{stock.sma}</td>
                        <td>{stock.ema}</td>
                        <td>{stock.rsi}</td>
                        <td>{stock.macd}</td>
                        <td>{stock.stochasticOscillator}</td>
                        <td>{stock.timestamp}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default StockTable;
