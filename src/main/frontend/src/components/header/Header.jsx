// Header.jsx

import React from "react"

class Header extends React.Component {
   constructor(props) {
      super(props)
      this.state = {
         isBurgerOpen: false,
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

   render() {
      return (
          <header className="header">
             <div className="container">
                <div className="header_nav">
                   <h1>{this.props.title}</h1>
                   <nav>
                      <div className="burger" onClick={this.toggleBurger}>
                         <span className="burger__line"></span>
                         <span className="burger__line"></span>
                         <span className="burger__line"></span>
                      </div>
                      <ul className="header_nav_menu">
                         <li>
                            <a href="/home">Главная страница</a>
                         </li>
                         <li>
                            <a href="/blogs">Блоги</a>
                         </li>
                         <li>
                            <a href="/authors">Авторы</a>
                         </li>
                         <li>
                            <a href="/profile">Профиль</a>
                         </li>
                      </ul>
                   </nav>
                </div>
             </div>
          </header>
      );
   }
}

export default Header;