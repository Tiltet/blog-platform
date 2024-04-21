// Header.jsx

import React from "react"
import { Link } from "react-router-dom"
import Cookies from "js-cookie";

class Header extends React.Component {

   constructor(props) {
      super(props)
      this.state = {
         isBurgerOpen: false,
         isTokenPresent: Cookies.get('token') ? true : false
      }
   }

   toggleBurger = () => {
      const burger = document.querySelector('.burger')
      const menu = document.querySelector('.header_nav_menu')

      if (menu.classList.contains('active')) {
         menu.classList.remove('active')
         burger.style.position = 'absolute'
      }
      else {
         menu.classList.add('active')
         burger.style.position = 'fixed'
      }
   }

   logout = () => {
      Cookies.remove('token')
      Cookies.remove('username')
      Cookies.remove('email')
      this.setState({ isTokenPresent: false })
   }

   render() {
      return (
          <header className="header">
             <div className="container">
                <div className="header_nav">
                   <h1>{this.props.title}</h1>
                   <nav>
                      <ul className="header_nav_menu">
                         <li>
                            <Link to={"/home"}>Главная страница</Link>
                         </li>
                         <li>
                            <Link to={"/blogs"}>Блоги</Link>
                         </li>
                         <li>
                            <Link to={"/authors"}>Авторы</Link>
                         </li>
                         {this.state.isTokenPresent ? (
                             <React.Fragment>
                                <li>
                                   <Link to="/profile">Профиль</Link>
                                </li>
                                <li>
                                   <Link to="/home" onClick={this.logout}>Выйти</Link>
                                </li>
                             </React.Fragment>
                         ) : (
                             <li>
                                <Link to="/signin">Войти</Link>
                             </li>
                         )}
                      </ul>
                      <div className="burger" onClick={this.toggleBurger}>
                         <span className="burger__line"></span>
                         <span className="burger__line"></span>
                         <span className="burger__line"></span>
                      </div>
                   </nav>
                </div>
             </div>
          </header>
      );
   }
}

export default Header;