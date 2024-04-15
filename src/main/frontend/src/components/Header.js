// Header.js

import React from "react"
import '../stylesheets/header.css'

class Header extends React.Component {
   render() {
      return (
         <header className="header">
            <div className="container">
               <div className="header_menu">
                  <h1>{this.props.title}</h1>
                  <nav>
                     <ul>
                        <li><a href="#">Главная</a></li>
                        <li><a href="#">О нас</a></li>
                        <li><a href="#">Услуги</a></li>
                        <li><a href="#">Контакты</a></li>
                     </ul>
                  </nav>
                  <button className="header_menu_button">
                     <p>Войти</p>
                  </button>
               </div>
            </div>
         </header>
        );  
    }
}

export default Header;