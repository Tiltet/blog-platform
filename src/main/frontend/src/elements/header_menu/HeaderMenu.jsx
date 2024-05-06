// HeaderMenu.jsx

import React from "react"
import {Link} from "react-router-dom";

class HeaderMenu extends React.Component {

    render() {
        return (
            <ul className="header_menu">
                <li>
                    <Link to="/home">Главная</Link>
                </li>
                <li>
                    <Link to="/blogs">Блоги</Link>
                </li>
                <li>
                    <Link to="/authors">Авторы</Link>
                </li>
            </ul>
        )
    }
}

export default HeaderMenu