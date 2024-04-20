// App.jsx

import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/header/Header";
import HomePage from "./pages/home/HomePage";
import SignIn from "./pages/signin/SignIn";
import Blogs from "./pages/blogs/Blogs";
import Authors from "./pages/authors/Authors";
import User from "./components/user/User";
import SignUp from "./pages/signup/SignUp";
import Profile from "./pages/profile/Profile";


class App extends React.Component {

    render() {
        return (
            <Router>
                <div className="App">
                    <Header title="Blog Platform"/>
                    <Routes>
                        <Route path="/home" element={<HomePage />} />
                        <Route path="/blogs" element={<Blogs/>} />
                        <Route path="/authors" element={<Authors/>} />
                        <Route path="/signin" element={<SignIn/>} />
                        <Route path="/signup" element={<SignUp/>} />
                        <Route path="/user" element={<User/>} />
                        <Route path="/profile" element={<Profile/>} />
                    </Routes>
                </div>
            </Router>
        );
    }
}

export default App;
