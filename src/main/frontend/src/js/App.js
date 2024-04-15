// App.js

import React, {useState, useEffect} from "react";
import '../stylesheets/App.css';
import axios from "axios";
import HomePage from './HomePage'

const UserProfiles = () => {

    const [userProfiles, setUserProfiles] = useState([]);

    const fetchUserProfiles = () => {
        axios.get("http://localhost:8080/api/v1/users").then(res => {
            console.log(res);
            setUserProfiles(res.data);
        });
    };

    useEffect(() => {
        fetchUserProfiles();
    }, []);

    return userProfiles.map((userProfile, index) => {
        return (
            <div key={index}>
                <h1>{userProfile.id}</h1>
                <p>{userProfile.username}</p>
            </div>
        );
    });    
}

function App() {
    return (
        <div className="App">
            <HomePage/>
            <UserProfiles />
        </div>
    );
}

export default App;
