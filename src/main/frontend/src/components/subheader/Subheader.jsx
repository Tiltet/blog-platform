// Subheader.jsx

import React from "react"
import Cookies from "js-cookie";
import HeaderProfile from "../../elements/header_profile/HeaderProfile";
import HeaderMenu from "../../elements/header_menu/HeaderMenu";

class Subheader extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            isBurgerOpen: false,
            isTokenPresent: !!Cookies.get('token')
        }
    }

    removeActive = () => {
        const menu = document.querySelector('.header_nav_menu')
        const lines = document.querySelectorAll('.burger_line')
        menu.classList.remove('active')
        lines.forEach(line => {
            line.classList.remove('active')
        })
    }

    render() {
        return (
            <header className="header">
                <div className="container">
                    <div className="header_nav">
                        <nav>
                            <div className="header_nav_menu_list">
                                <HeaderMenu/>
                            </div>
                            <div className="header_nav_menu_profile">
                                <HeaderProfile/>
                            </div>
                        </nav>
                    </div>
                </div>
            </header>
        );
    }
}

export default Subheader;