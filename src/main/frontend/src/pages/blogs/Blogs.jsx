// Blogs.jsx

import React, { useState, useEffect } from "react";
import axios from "axios";

const baseUrl = "http://localhost:8080/api/v2/blogs"

const BlogProfiles = () => {

    const [blogProfiles, setBlogProfiles] = useState([]);

    const fetchBlogProfiles = () => {
        axios.get(baseUrl).then(res => {
            console.log(res);
            setBlogProfiles(res.data);
        });
    };

    useEffect(() => {
        fetchBlogProfiles();
    }, []);

    return blogProfiles.map((blogProfile, index) => {
        return (
            <div className="blog" key={index}>
                <h2>{blogProfile.title}</h2>
                <div className="blog_container_img">
                    <img src={blogProfile.img} alt=""/>
                </div>
                <p>{blogProfile.description}</p>
            </div>
        );
    });
}

class Blogs extends React.Component {
    render() {
        return (
            <div className="container">
                <div className="blogs">
                    <BlogProfiles />
                </div>
            </div>
        );
    }
}

export default Blogs;