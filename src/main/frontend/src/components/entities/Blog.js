// Blog.js

import React from "react"

class Blog extends React.Component {
    blog = this.props.blog;
    render() {
        return (
            <div className="blog">
                <h3>{this.blog.title}</h3>
                <p>{this.blog.description}</p>
            </div>
        );  
    }
}

export default Blog;