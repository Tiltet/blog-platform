// SignIn.jsx

import React from "react"
import {Link, useNavigate} from "react-router-dom"
import axios from "axios";

const baseUrl = "http://localhost:8080/auth/signin";

const SignIn = () => {

    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();

        const formData = new FormData(event.target);
        const data = {
            username: formData.get("username"),
            password: formData.get("password"),
        };

        console.log(data.username);
        console.log(data.password);

        axios
            .post(baseUrl, data)
            .then((response) => {
                const token = response.data;
                localStorage.setItem('token', token);
                console.log(localStorage.getItem('token'));
                navigate("/home");
                window.location.reload();
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };

    return (
        <div className="container">
            <div className="block_signin">
                <form className="form_signin" onSubmit={handleSubmit}>
                    <div className="form_group">
                        <label htmlFor="username">Логин:</label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            placeholder="Username"
                            autoComplete="username"
                            required
                        />
                    </div>
                    <div className="form_group">
                        <label htmlFor="password">Пароль:</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="Password"
                            autoComplete="password"
                            required
                        />
                    </div>
                    <button className="signIn_button" type="submit">
                        Войти
                    </button>
                    <div className="form_separator">
                        <div className="horizontal-line"></div>
                        <div className="content">or</div>
                        <div className="horizontal-line"></div>
                    </div>
                    <button className="signUp_button" type="submit">
                        <Link to={"/signup"}>Регистрация</Link>
                    </button>
                </form>
            </div>
        </div>
    );
}

export default SignIn;