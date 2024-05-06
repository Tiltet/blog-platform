import React from "react";
import { Link } from "react-router-dom";
import Cookies from 'js-cookie';
import Button from "../../elements/buttons/Button";

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
        else if (this.avatar) {
            console.log(this.avatar)
            this.avatar = "https://abrakadabra.fun/uploads/posts/2021-12/thumbs/1640528715_49-abrakadabra-fun-p-serii-chelovek-na-avu-56.jpg"
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
                                <div className="profile_block">
                                    <img src={this.avatar} alt="Avatar"/>
                                    <div className="profile_info">
                                        <h2>Профиль</h2>
                                        <p>Username: {Cookies.get('username')}</p>
                                        <p>Email: {Cookies.get('email')}</p>
                                        <Link to={"/profile/addBlog"}>
                                            <Button title={'Добавить блог'} className={'profile_addBlog_button'} />
                                        </Link>
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
