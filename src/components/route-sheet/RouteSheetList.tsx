import React, { useEffect, useState } from 'react';
import { Plus } from 'lucide-react';
import { RouteSheetDTO } from '../../types';
import { api } from '../../services/api';

export const RouteSheetList = () => {
  const [routeSheets, setRouteSheets] = useState<RouteSheetDTO[]>([]);

  useEffect(() => {
    loadRouteSheets();
  }, []);

  const loadRouteSheets = async () => {
    try {
      const response = await api.get('/route-sheets');
      setRouteSheets(response.data);
    } catch (error) {
      console.error('Error loading route sheets:', error);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Route Sheets</h1>
        <button className="flex items-center px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700">
          <Plus className="h-5 w-5 mr-2" />
          New Route Sheet
        </button>
      </div>
      <div className="bg-white shadow overflow-hidden sm:rounded-md">
        <ul className="divide-y divide-gray-200">
          {routeSheets.map((sheet) => (
            <li key={sheet.id} className="px-6 py-4 hover:bg-gray-50">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm font-medium text-indigo-600">{sheet.routeNumber}</p>
                  <p className="text-sm text-gray-500">{sheet.description}</p>
                </div>
                <div className="flex items-center">
                  <span className={`px-2 py-1 text-xs rounded-full ${
                    sheet.status === 'OPEN' ? 'bg-green-100 text-green-800' : 'bg-gray-100 text-gray-800'
                  }`}>
                    {sheet.status}
                  </span>
                </div>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};