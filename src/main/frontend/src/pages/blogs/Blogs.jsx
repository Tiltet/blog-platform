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
                <p className="blog_category">Новость</p>
                <div className="blog_author">
                    <img src={blogProfile.author.avatar} alt="" className="blog_author_avatar"/>
                    <div className="blog_author_name">{blogProfile.author.username}</div>
                </div>
                <h2 className="blog_title">{blogProfile.title}</h2>
                <div className="blog_background">
                    <img src={blogProfile.img} alt="blog_img"/>
                </div>
                <p className="blog_description">{blogProfile.description}</p>
                <button className="blog_button">Читать далее</button>
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