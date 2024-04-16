// App.js

import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Blogs from "./components/Blogs";
import Users from "./components/Users";

class App extends React.Component {

    render() {
        return (
            <Router>
                <div className="App">
                    <Header title="Blog Platform"/>
                    <Routes>
                        <Route path="/blogs" element={<Blogs />} />
                        <Route path="/users" element={<Users />} />
                    </Routes>
                </div>
            </Router>
        );
    }
}

export default App;
