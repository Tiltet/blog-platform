// Authors.jsx

import React, {useEffect, useState} from "react"
import { Link } from "react-router-dom";
import axios from "axios";
import Button from "../../elements/buttons/Button";

const baseUrl = "http://localhost:8080/api/v1/getAuthors"

const UserProfiles = () => {

    const [userProfiles, setUserProfiles] = useState([]);

    const fetchUserProfiles = () => {
        axios
            .get(baseUrl)
            .then(res => {
            setUserProfiles(res.data);
            console.log(res.data);
        });
    };

    useEffect(() => {
        fetchUserProfiles();
    }, []);

    return userProfiles.map((userProfile, index) => {
        return (
            <div className="author" key={index}>
                <img src={userProfile.avatar} alt=""/>
                <h1>{userProfile.username}</h1>
                <p>{userProfile.email}</p>
                <Link to={`/user?id=${userProfile.id}`}>
                    <Button title={'Профиль'} />
                </Link>
            </div>
        );
    });
}

class Authors extends React.Component {
    render() {
        return (
                <div className="container">
                    <div className="authors">
                        <UserProfiles/>
                    </div>
                </div>
        );
    }
}

export default Authors;