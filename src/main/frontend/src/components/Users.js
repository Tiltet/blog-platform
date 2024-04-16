// Users.js

import React from "react"
import User from "./entities/User";

class Users extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            users: [
                {
                    id: 1,
                    username: "timofey",
                    password: "31082004",
                    email: "timofeysavinich@mail.ru"
                },
                {
                    id: 2,
                    username: "timofey2",
                    password: "31082004",
                    email: "timofeysavinich@mail.ru"
                }
            ]
        }
    }

    render() {
        return (
            <div className="container">
                {this.state.users.map((element) => (
                    <User key={element.id} user={element} />
                ))}
            </div>
        );
    }
}

export default Users;