// SignIn.jsx

import React from "react"
import { Link, useNavigate } from "react-router-dom"
import axios from "axios";
import Button from "../../elements/buttons/Button";
import Cookies from 'js-cookie';

const baseUrl = "http://localhost:8080/auth/signin";
const getUser = "http://localhost:8080/api/v1/security/profile";

const SignIn = () => {

    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();

        const formData = new FormData(event.target);
        const data = {
            username: formData.get("username"),
            password: formData.get("password"),
            email: formData.get("email"),
            avatar: formData.get("avatar"),
        };

        console.log(data.username);
        console.log(data.password);

        axios
            .post(baseUrl, data)
            .then((response) => {
                const token = response.data;

                // TODO: save token in local storage
                localStorage.setItem('token', token);

                axios
                    .get(getUser, {
                    params: {
                        username: data.username
                    },
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {
                        Cookies.set('token', token);
                        Cookies.set('id', response.data.id);
                        Cookies.set('username', response.data.username);
                        Cookies.set('email', response.data.email);
                        Cookies.set('avatar', response.data.avatar);
                        navigate("/profile");
                        window.location.reload();
                    })
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };

    return (
        <div className="container">
            <div className="block">
                <div className="block_signin">
                    <h2>Вход</h2>
                    <form className="form_signin" onSubmit={handleSubmit}>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            placeholder="Username"
                            autoComplete="username"
                            required
                        />
                        <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="Password"
                            autoComplete="password"
                            required
                        />
                        <Button title={'Войти'} className={'signIn_button'}/>
                    </form>
                    <div className="signin_footer">
                        <p>Нет аккаунта?</p>
                        <Link to={"/signup"}>
                            Регистрация
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SignIn;