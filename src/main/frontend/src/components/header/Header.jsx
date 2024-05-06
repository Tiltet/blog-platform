// Header.jsx

import React from "react"
import HeaderProfile from "../../elements/header_profile/HeaderProfile";
import HeaderMenu from "../../elements/header_menu/HeaderMenu";
import HeaderServices from "../../elements/header_services/HeaderServices";

class Header extends React.Component {

   constructor(props) {
      super(props);
      this.subheaderContainerRef = React.createRef();
   }

   handleClickOutside = (event) => {
      if (
          this.subheaderContainerRef.current &&
          !this.subheaderContainerRef.current.contains(event.target)
      ) {
         const list = document.querySelector(".subheader_container_left_list");
         const menu = document.querySelector(".subheader_container_menu");
         list.classList.remove("active");
         menu.classList.remove('active')
         document.body.style.overflow = 'auto';
      }
   }

   componentDidMount() {
      document.addEventListener("click", this.handleClickOutside);
   }

   componentWillUnmount() {
      document.removeEventListener("click", this.handleClickOutside);
   }


   ButtonArrowClick = () => {
      const list = document.querySelector(".subheader_container_left_list");
      list.classList.toggle("active");
   }

   tooggleBurger = () => {
      const menu = document.querySelector('.subheader_container_menu')
      const lines = document.querySelectorAll('.subheader_burger_line')

      if (menu.classList.contains('active')) {
         menu.classList.remove('active')
         lines.forEach(line => {
            line.classList.remove('active')
         })
         document.body.style.overflow = 'auto';
      }
      else {
         menu.classList.add('active')
         lines.forEach(line => {
            line.classList.add('active')
         })
         document.body.style.overflow = 'hidden';
      }
   }

   render() {
      return (
          <div className="subheader" ref={this.subheaderContainerRef}>
             <div className="container">
                <div className="subheader_container">
                   <div className="subheader_container_left">
                      <div className="subheader_burger" onClick={this.tooggleBurger}>
                         <span className="subheader_burger_line"></span>
                         <span className="subheader_burger_line"></span>
                         <span className="subheader_burger_line"></span>
                      </div>
                      <div className="subheader_container_menu">
                         <div className="subheader_container_menu_wrapper">
                            <h3 className="subheader_container_menu_title">Меню</h3>
                            <HeaderMenu/>
                         </div>
                         <div className="subheader_container_menu_wrapper">
                            <HeaderServices/>
                         </div>
                      </div>
                      <h1 className="subheader_container_left_title">
                      <a href="#">{this.props.title}</a>
                      </h1>
                      <button className="subheader_container_left_buttonArrow" onClick={this.ButtonArrowClick}>
                         <img src="img/icons/icons8-arrow-down.png" alt=""/>
                      </button>
                      <button className="subheader_container_left_buttonAuthor">
                         <span>Как стать автором</span>
                      </button>
                      <div className="subheader_container_left_list">
                         <HeaderServices/>
                      </div>
                   </div>
                   <div className="subheader_container_rigth">
                      <a href="#" className="subheader_container_rigth_text">
                         Как расширяется вселенная кода GitVerse
                      </a>
                   </div>
                   <div className="subheader_container_profile">
                      <HeaderProfile/>
                   </div>
                </div>
             </div>
          </div>

      )
   }
}

export default Header