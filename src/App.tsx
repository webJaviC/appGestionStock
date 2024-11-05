import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './hooks/useAuth';
import { LoginForm } from './components/auth/LoginForm';
import { Navbar } from './components/layout/Navbar';
import { RouteSheetList } from './components/route-sheet/RouteSheetList';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="min-h-screen bg-gray-100">
          <Navbar />
          <Routes>
            <Route path="/login" element={<LoginForm />} />
            <Route path="/route-sheets" element={<RouteSheetList />} />
            <Route path="/" element={<Navigate to="/route-sheets" replace />} />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;