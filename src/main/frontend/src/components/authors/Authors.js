// Authors.js

import React, {useEffect, useState} from "react"
import { Link } from "react-router-dom";
import axios from "axios";

const baseUrl = "http://localhost:8080/api/v1/getAuthors"

const UserProfiles = () => {

    const [userProfiles, setUserProfiles] = useState([]);

    const fetchUserProfiles = () => {
        axios.get(baseUrl).then(res => {
            console.log(res);
            setUserProfiles(res.data);
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
                <Link
                    to={{
                        pathname: "/user",
                        state: { userProfile: userProfile },
                    }}
                >
                    <button>Профиль</button>
                </Link>
            </div>
        );
    });
}

class Authors extends React.Component {
    constructor(props) {
        super(props)

        axios.get(baseUrl).then((res) => {
            console.log(res.data)
        })
    }

    render() {
        return (
            <div className="container">
                <div className="authors">
                    <UserProfiles />
                </div>
            </div>
        );
    }
}

export default Authors;