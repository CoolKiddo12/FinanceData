import React from 'react';
import './App.css';
import StockAnalysisTable from './StockAnalysisTable';  // Import the table component

function App() {
    return (
        <div className="App">
            <h1>Stock Analysis Dashboard</h1>
            <StockAnalysisTable />  {/* Add the StockAnalysisTable component */}
        </div>
    );
}

export default App;
