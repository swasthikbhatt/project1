import {useEffect, useState} from 'react'
import './App.css'

const BACKEND_SPRINGBOOT_BASE_URL = import.meta.env.VITE_BACKEND_BASE_URL;

// This is a React functional component. In React, a component is a reusable piece of UI. Your App component is responsible for displaying your application's interface. When React renders this component, it executes the App() function and uses the returned JSX to display the UI.
function App()
{
    /*  A state variable is simply a variable whose value React remembers and monitors. When its value changes, React automatically updates the parts of the webpage that depend on it.
        State variable = Data that a React component remembers between renders and whose updates can trigger the component to re-render.
        useState() creates a state variable.
        This creates a piece of React state named title. title is the variable you read to get the current state value. setTitle() is the function you call to request a state update. "Default Title" is the initial value, not necessarily the value forever.
        whenever setTitle() executes, the entire App component function re-runs (re-renders).
     */
    const [title, setTitle] = useState('Loading...')

    /*  When does it execute?
        It executes automatically after the component initially mounts. You don't need to click any button.
        For example:
        1. You open http://localhost:5173.
        2. React renders the App component.
        3. useEffect() executes after the initial render.
        4. React sends a GET request to http://localhost:8081/.
        5. Spring Boot returns a response.
        6. setTitle(text) updates the state.
        7. React re-renders the App component.
     */
    useEffect(() =>
    {
        fetch(`${BACKEND_SPRINGBOOT_BASE_URL}/`, { method: 'GET' }) // same as fetch('http://localhost:8081/', {method: 'GET'}) -> This sends an HTTP request to your Spring Boot backend. Spring Boot receives it and looks for a controller method mapped to /.
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
                return response.text();
            }) // This reads the response body as text. The response body is the string returned by your Spring Boot controller method.
            .then(text => setTitle(text)) // This updates the title state with the value from the API response.
            .catch(error => console.error('Error fetching title:', error)) // This logs any errors that occur during the fetch operation to the console.
    }, [])

    /*  fetchPage is an ordinary JavaScript function that you created yourself.
        It is not a built-in React function or a React Hook.
     */
    const fetchPage = (path) =>
    {
        fetch(`${BACKEND_SPRINGBOOT_BASE_URL}${path}`, { method: 'GET' }) // same as fetch(`http://localhost:8081${path}`, {method: 'GET'})
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

// This is used to make your App component available to other JavaScript files so they can import and use it.
export default App