// Header.jsx

import React from "react"
import { Link } from "react-router-dom"

class Header extends React.Component {

   constructor(props) {
      super(props)
      this.state = {
         isBurgerOpen: false,
         isTokenPresent: false,
      }
   }

   componentDidMount() {
      const token = localStorage.getItem('token');
      if (token) {
         this.setState({ isTokenPresent: true });
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

   printToken = () => {
      console.log(localStorage.getItem("token"));
   }

   logout = () => {
      localStorage.removeItem("token")
      window.location.reload();
   }

   render() {

      const { isTokenPresent } = this.state;

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
                         {
                            isTokenPresent === false ?
                                <li>
                                   <Link to={"/signin"}>Войти</Link>
                                </li>
                            : false
                         }
                         {
                            isTokenPresent ? (
                                <React.Fragment>
                                   <li>
                                      <Link to={"/profile"} onClick={this.printToken}>Профиль</Link>
                                   </li>
                                   <li>
                                      <Link to={"/home"} onClick={this.logout}>Выйти</Link>
                                   </li>
                                </React.Fragment>
                            ) : false
                         }
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