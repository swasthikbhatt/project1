import {useEffect, useState} from 'react'
import './App.css'

function App()
{
    const [title, setTitle] = useState('Loading...') // This creates a piece of React state named title. title is the variable you read to get the current state value. setTitle() is the function you call to request a state update. "Default Title" is the initial value, not necessarily the value forever.

    useEffect(() =>
    {
        fetch('http://localhost:8081/', {method: 'GET'})// This sends an HTTP request to your Spring Boot backend. Spring Boot receives it and looks for a controller method mapped to /.
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
                return response.text();
            }) // This reads the response body as text. The response body is the string returned by your Spring Boot controller method.
            .then(text => setTitle(text)) // This updates the title state with the value from the API response.
            .catch(error => console.error('Error fetching title:', error)) // This logs any errors that occur during the fetch operation to the console.
    }, [])

    const fetchPage = (path) =>
    {
        fetch(`http://localhost:8081${path}`, {method: 'GET'})
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
                return response.text();
            })
            .then(text => setTitle(text))
            .catch(error => {
                console.error('Error fetching page:', error);
                setTitle('Unable to load page. Please try again.');
            });
    };

    return (
        <div>
            <h1>My React Application</h1>
            <button onClick={() => fetchPage('/')}>Home</button>
            <button onClick={() => fetchPage('/profile')}>Profile</button>
            <button onClick={() => fetchPage('/settings')}>Settings</button>
            <h2>{title}</h2>
        </div>
    )
}

export default App