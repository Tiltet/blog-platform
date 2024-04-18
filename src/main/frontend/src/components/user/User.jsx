// User.jsx

import React, { useEffect, useState } from "react"
import { useLocation } from "react-router-dom";
import axios from 'axios';
import Button from "../../elements/buttons/Button";

const User = () => {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const id = queryParams.get("id");
    const [user, setUser] = useState([]);

    const fetchUser = async () => {
        axios.get(`http://localhost:8080/api/v1/user?id=${id}`).then(res => {
            setUser(res.data);
            console.log(res.data);
        });
    }

    useEffect(() => {
        fetchUser();
    }, [id]);

    return (
        <div className="container">
            <div>
                <div className="container">
                    <div className="profile">
                        <img src={user.avatar} alt=""/>
                        <div className="profile_description">
                            <div className="profile_description_text">
                                <h3>{user.id} </h3>
                                <p>{user.username} </p>
                                <p>{user.email} </p>
                            </div>
                            <Button title={'Блоги'} />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default User;