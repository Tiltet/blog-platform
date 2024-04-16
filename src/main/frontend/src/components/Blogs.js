// Blogs.js

import React, { useState, useEffect } from "react";
import axios from "axios";

const baseUrl = "http://localhost:8080/api/v2/blogs"

const BlogProfiles = () => {

    const [blogProfiles, setBlogProfiles] = useState([]);

    const fetchUserProfiles = () => {
        axios.get(baseUrl).then(res => {
            console.log(res);
            setBlogProfiles(res.data);
        });
    };

    useEffect(() => {
        fetchUserProfiles();
    }, []);

    return blogProfiles.map((blogProfile, index) => {
        return (
            <div key={index}>
                <h1>{blogProfile.id}</h1>
                <p>{blogProfile.title}</p>
                <p>{blogProfile.description}</p>
            </div>
        );
    });    
}

class Blogs extends React.Component {
    constructor(props) {
        super(props)

        axios.get(baseUrl).then((res) => {
            console.log(res.data)
        })

        this.state = {
            blogs: [
                {
                    id: 1,
                    title: 'blog_1',
                    description: 'description_1'
                },
                {
                    id: 2,
                    title: 'blog_2',
                    description: 'description_2'
                }
            ]
        }
    }

    render() {
        return (
            // <div className="container">
            //     {this.state.blogs.map((element) => (
            //         <Blog key={element.id} blog={element} />
            //     ))}
            // </div>

            <div className="container">
                {/* <h1>Blogs</h1> */}
                <BlogProfiles />
            </div>
        );
    }
}

export default Blogs;