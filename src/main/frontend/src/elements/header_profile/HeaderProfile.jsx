// HeaderProfile.jsx

import React from "react";
import { Link } from "react-router-dom";
import Cookies from "js-cookie";

class HeaderProfile extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isTokenPresent: !!Cookies.get('token')
        }
    }

    logout = () => {
        Cookies.remove('token')
        Cookies.remove('id')
        Cookies.remove('username')
        Cookies.remove('email')
        Cookies.remove('avatar')
        this.setState({ isTokenPresent: false })
    }

    render() {
        return (
            <ul className="header_profile">
                <li>
                    <Link to="#">
                        <img className="header_profile_icons" src="img/icons/icons8-поиск.svg" alt=""/>
                    </Link>
                </li>
                <li>
                    <Link to="#">
                        <img className="header_profile_icons" src="img/icons/icons8-карандаш-32.svg" alt=""/>
                    </Link>
                </li>
                {this.state.isTokenPresent ? (
                    <React.Fragment>
                        <li>
                            <Link to="/profile" onClick={this.removeActive}>
                                <img className="header_profile_avatar" src={Cookies.get('avatar')} alt=""/>
                            </Link>
                        </li>
                        <li>
                            <Link to="/home" onClick={this.logout}>Выйти</Link>
                        </li>
                    </React.Fragment>
                ) : (
                    <React.Fragment>
                        <li className="header_profile_signin">
                            <Link to="/signin" onClick={this.removeActive}>Войти</Link>
                        </li>
                    </React.Fragment>
                )}
            </ul>
        )
    }
}

export default HeaderProfile