// Blogs.js

import React from "react"
import Blog from "./entities/Blog";
import axios from "axios";

// const baseUrl = "http://localhost:8080/api/v2/blogs"

// const BlogProfiles = () => {

//     const [userProfiles, setUserProfiles] = useState([]);

//     const fetchUserProfiles = () => {
//         axios.get("http://localhost:8080/api/v1/users").then(res => {
//             console.log(res);
//             setUserProfiles(res.data);
//         });
//     };

//     useEffect(() => {
//         fetchUserProfiles();
//     }, []);

//     return userProfiles.map((userProfile, index) => {
//         return (
//             <div key={index}>
//                 <h1>{userProfile.id}</h1>
//                 <p>{userProfile.username}</p>
//             </div>
//         );
//     });    
// }

class Blogs extends React.Component {
    constructor(props) {
        super(props)

        // axios.get(baseUrl).then((res) => {
        //     console.log(res.data)
        // })

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
            <div className="container">
                {this.state.blogs.map((element) => (
                    <Blog key={element.id} blog={element} />
                ))}
            </div>
        );
    }
}

export default Blogs;