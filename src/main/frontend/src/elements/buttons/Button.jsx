// Button.jsx

import React from "react"

const Button = ( {title} ) =>  {

    return (
        <button className="greenButton">
            <p>{title}</p>
        </button>
    );
}

export default Button;