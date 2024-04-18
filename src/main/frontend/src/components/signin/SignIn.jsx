// SignIn.jsx

import React from "react"

class SignIn extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            login: "",
            password: ""
        };
    }

    handleInputChange = (event) => {
        const { name, value } = event.target;
        this.setState({
            [name]: value
        });
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const { login, password } = this.state;
        console.log("Логин:", login);
        console.log("Пароль:", password);
    };
    render() {
        const { login, password } = this.state;

        return (
            <div className="container">
                <div className="block_signin">
                    <form className="form_signin" onSubmit={this.handleSubmit}>
                        <div className="form_group">
                            <label htmlFor="login">Логин:</label>
                            <input
                                type="text"
                                id="login"
                                name="login"
                                value={login}
                                onChange={this.handleInputChange}
                                required
                            />
                        </div>
                        <div className="form_group">
                            <label htmlFor="password">Пароль:</label>
                            <input
                                type="password"
                                id="password"
                                name="password"
                                value={password}
                                onChange={this.handleInputChange}
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
                            Регистрация
                        </button>
                    </form>
                </div>
            </div>
        );
    }
}

export default SignIn;