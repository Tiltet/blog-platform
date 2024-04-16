// User.js

import React from "react"

class User extends React.Component {
    user = this.props.user;
    render() {
        return (
            <div className="user">
                <h3>{this.user.id}</h3>
                <p>{this.user.username}</p>
            </div>
        );  
    }
}

export default User;