// Button.jsx

import React from "react"

const Button = ( {title, className} ) =>  {

    return (
        <button className={`greenButton ${className}`}>
            <p>{title}</p>
        </button>
    );
}

export default Button;