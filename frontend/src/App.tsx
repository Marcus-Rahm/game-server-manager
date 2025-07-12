import { useEffect, useState } from "react";

function App() {
  const [healthStatus, setHealthStatus] = useState('Loading...');
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    // Define an asynchronous function to fetch data
    const fetchHealth = async () => {
      try {
        // Make the GET request to your Quarkus health endpoint
        // Remember, frontend runs on 5173, backend on 8080
        const response = await fetch('http://localhost:8080/q/health/live');

        // Check if the response was successful (status code 200-299)
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Parse the JSON response
        const data = await response.json();

        // Update the health status state
        setHealthStatus(data.status); // The health endpoint returns { "status": "UP" | "DOWN", ... }

      } catch (err) {
        // Catch any network errors or errors from the fetch call
        console.error("Failed to fetch health check:", err);
        setError("Could not connect to backend health endpoint.");
        setHealthStatus("DOWN"); // Set status to DOWN if an error occurs
      }
    };

    // Call the fetch function
    fetchHealth();
  }, []);

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col items-center justify-center">
      <h1 className="text-5xl font-bold text-blue-600 mb-4">
        Welcome to RabbitHole!
      </h1>
      {error ? ( // Conditionally render error message
        <p className="text-red-600 text-lg">Error: {error}</p>
      ) : (
        <p className="text-gray-800 text-2xl">
          Backend Health: <span className={`font-semibold ${healthStatus === 'UP' ? 'text-green-500' : 'text-red-500'}`}>{healthStatus}</span>
        </p>
      )}
    </div>
  );
}

export default App;