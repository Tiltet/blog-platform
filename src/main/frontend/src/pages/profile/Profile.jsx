import React from "react";
import { Link } from "react-router-dom";
import Cookies from 'js-cookie';

class Profile extends React.Component {

    avatar = Cookies.get('avatar');
    constructor(props) {
        super(props)
        this.state = {
            isTokenPresent: false,
        }
    }

    componentDidMount() {
        const token = Cookies.get('token');
        if (token) {
            this.setState({ isTokenPresent: true });
        }

        if (this.avatar) {
            console.log(this.avatar)
            this.avatar = "https://i.pinimg.com/236x/9e/8c/e1/9e8ce1ae6503ff0538f32d5bd7bd23b5.jpg"
        }
    }

    render() {

        const { isTokenPresent } = this.state;

        return (
            <div className="profile">
                <div className="container">
                    {
                        isTokenPresent ? (
                            <React.Fragment>
                                <div className="profile">
                                    <img src={this.avatar} alt="Avatar"/>
                                    <div className="profile_info">
                                        <h2>Пользователь зарегистрирован</h2>
                                        <p>Username: {Cookies.get('username')}</p>
                                        <p>Email: {Cookies.get('email')}</p>
                                        <Link to={"/home"}>Главная страница</Link>
                                    </div>
                                </div>
                            </React.Fragment>
                        ) : (
                            <React.Fragment>
                                <div className="profile_item">
                                    <h2>Вы не вошли в аккаунт</h2>
                                    <Link to={"/signin"}>Войти в аккаунт</Link>
                                </div>
                            </React.Fragment>
                        )
                    }
                </div>
            </div>
        );
    }
}

export default Profile;
