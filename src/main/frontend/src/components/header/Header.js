// Header.js

import React from "react"

class Header extends React.Component {

   render() {
      return (
         <header className="header">
            <div className="container">
               <div className="header_menu">
                  <h1>{this.props.title}</h1>
                  <nav>
                     <ul>
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