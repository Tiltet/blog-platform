// User.js

import React from "react"

const User = ({ location }) => {
    const userProfile = location.state.userProfile;

    return (
        <div>
            <h1>Профиль пользователя</h1>
            <h2>Имя: {userProfile.username}</h2>
            <p>Email: {userProfile.email}</p>
        </div>
    );
}

export default User;