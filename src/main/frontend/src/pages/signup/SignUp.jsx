import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Button from "../../elements/buttons/Button";

const baseUrl = "http://localhost:8080/auth/signup";

const SignUp = () => {
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();

        const formData = new FormData(event.target);
        const data = {
            username: formData.get("username"),
            password: formData.get("password"),
            email: formData.get("email"),
        };

        axios
            .post(baseUrl, data)
            .then((response) => {
                console.log(response.data);
                navigate("/home");
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };

    return (
        <div className="container">
            <div className="signup">
                <h2>Регистрация</h2>
                <form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        name="username"
                        placeholder="Username"
                        autoComplete="username"
                    />
                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        autoComplete="current-password"
                    />
                    <input
                        type="email"
                        name="email"
                        placeholder="Email"
                        autoComplete="email"
                    />
                    <Button title={"Зарегистрироваться"} className={"signup_button"}/>
                </form>
            </div>
        </div>
    );
};

export default SignUp;