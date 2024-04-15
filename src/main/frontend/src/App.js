// App.js

import React, {useState, useEffect} from "react";
import Header from "./components/Header";
import Blogs from "./components/Blogs";
import HomePage from "./components/HomePage";
import User from "./components/entities/User";

class App extends React.Component {

    render() {
        return (
            <div className="App">
                <Header title="Blog Platform"/>
                <Blogs/>
                {/* <User/> */}
                <HomePage/>
            </div>
        );
    }
}

export default App;
