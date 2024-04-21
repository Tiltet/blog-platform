import React from "react";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
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
            avatar: "https://abrakadabra.fun/uploads/posts/2021-12/thumbs/1640528715_49-abrakadabra-fun-p-serii-chelovek-na-avu-56.jpg"
        };

        axios
            .post(baseUrl, data)
            .then((response) => {
                console.log(response.data);
                navigate("/signin");
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };

    return (
        <div className="container">
            <div className="signup_block">
                <div className="signup_form">
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
                    <div className="signup_footer">
                        <p>Есть аккаунт?</p>
                        <Link to={"/signin"}>
                            Войти
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SignUp;