// Header.jsx

import React from "react"
import { Link } from "react-router-dom"
import Cookies from "js-cookie";

class Header extends React.Component {

   constructor(props) {
      super(props)
      this.state = {
         isBurgerOpen: false,
         isTokenPresent: !!Cookies.get('token')
      }
   }

   toggleBurger = () => {
      const burger = document.querySelector('.burger')
      const menu = document.querySelector('.header_nav_menu')
      const lines = document.querySelectorAll('.burger_line')

      if (menu.classList.contains('active')) {
         menu.classList.remove('active')
         lines.forEach(line => {
            line.classList.remove('active')
         })
      }
      else {
         menu.classList.add('active')
         lines.forEach(line => {
            line.classList.add('active')
         })
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
                   <h1>{this.props.title}</h1>
                   <nav>
                      <ul className="header_nav_menu">
                         <li>
                            <Link to={"/home"} onClick={this.removeActive}>Главная страница</Link>
                         </li>
                         <li>
                            <Link to={"/blogs"} onClick={this.removeActive}>Блоги</Link>
                         </li>
                         <li>
                            <Link to={"/authors"} onClick={this.removeActive}>Авторы</Link>
                         </li>
                         {this.state.isTokenPresent ? (
                             <React.Fragment>
                                <li>
                                   <Link to="/profile" onClick={this.removeActive}>Профиль</Link>
                                </li>
                                <li>
                                   <Link to="/home" onClick={this.logout}>Выйти</Link>
                                </li>
                             </React.Fragment>
                         ) : (
                             <li>
                                <Link to="/signin" onClick={this.removeActive}>Войти</Link>
                             </li>
                         )}
                      </ul>
                      <div className="burger" onClick={this.toggleBurger}>
                         <span className="burger_line"></span>
                         <span className="burger_line"></span>
                         <span className="burger_line"></span>
                      </div>
                   </nav>
                </div>
             </div>
          </header>
      );
   }
}

export default Header;