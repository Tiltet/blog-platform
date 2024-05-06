// HeaderServices.jsx

import React from "react";

class HeaderServices extends React.Component {
    render() {
        return (
            <ul className="header_services">
                <h2>Все сервисы Хабра</h2>
                <a href="#">
                    <h3>Хабр</h3>
                    <p>Сообщество IT специалистов</p>
                </a>
                <a href="#">
                    <h3>Q&A</h3>
                    <p>Ответы на вопросы об IT</p>
                </a>
                <a href="#">
                    <h3>Карьера</h3>
                    <p>Профессиональное развитие в IT</p>
                </a>
                <a href="#">
                    <h3>Фриланс</h3>
                    <p>Удаленная работа для IT специалистов</p>
                </a>
            </ul>
        )
    }
}

export default HeaderServices