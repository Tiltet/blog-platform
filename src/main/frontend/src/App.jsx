// App.jsx

import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/header/Header";
import HomePage from "./pages/home/HomePage";
import SignIn from "./components/signin/SignIn";
import Blogs from "./components/blogs/Blogs";
import Authors from "./components/authors/Authors";
import User from "./components/user/User";

class App extends React.Component {

    render() {
        return (
            <Router>
                <div className="App">
                    <Header title="Blog Platform"/>
                    <Routes>
                        <Route path="/home" element={<HomePage />} />
                        <Route path="/blogs" element={<Blogs/>} />
                        <Route path="/authors/*" element={<Authors/>} />
                        <Route path="/profile" element={<SignIn/>} />
                        <Route path="/user" element={<User/>} />
                    </Routes>
                </div>
            </Router>
        );
    }
}

export default App;
