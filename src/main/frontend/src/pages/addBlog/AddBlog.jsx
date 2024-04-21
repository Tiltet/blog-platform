import React from "react"
import Button from "../../elements/buttons/Button";
import axios from "axios";
import Cookies from "js-cookie";

const baseUrl = "http://localhost:8080/api/v2/addBlog";

class AddBlog extends React.Component {

    constructor(props) {
        super(props);
    }

    addBlog = (event) => {
        event.preventDefault();

        const formData = new FormData(event.target);
        const data = {
            title: formData.get("title"),
            description: formData.get("description"),
            img: "https://abrakadabra.fun/uploads/posts/2021-12/thumbs/1640528715_49-abrakadabra-fun-p-serii-chelovek-na-avu-56.jpg"
        };

        console.log(data)
        console.log(Cookies.get('id'))

        axios
            .post(`${baseUrl}?authorId=${Cookies.get('id')}`, {
                title: data.title,
                description: data.description,
                img: data.img,
            })
            .then((response) => {
                console.log("nice");
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    }

    render() {
        return (
            <div className="addBlog">
                <div className="container">
                    <div className="addBlog_block">
                        <h2 className="addBlog_title">Новый блог</h2>
                        <form className="addBlog_form" onSubmit={this.addBlog}>
                            <div className="addBlog_form_item">
                                {/*<label htmlFor="title">Название</label>*/}
                                <input type="text"
                                       id="title"
                                       name="title"
                                       placeholder="Название блога"
                                />
                            </div>
                            <div className="addBlog_form_item">
                               {/* <label htmlFor="description">Описание</label>*/}
                                <input type="text"
                                       id="description"
                                       name="description"
                                       placeholder="Описание блога"
                                />
                            </div>
                            <div className="addBlog_form_item">
                                {/*<label htmlFor="img">Изображение</label>*/}
                                <input type="text"
                                       id="img"
                                       name="img"
                                       placeholder="Ссылка на изображение"
                                />
                            </div>
                            <div className="addBlog_form_item">
                                <Button title={'Добавить блог'}/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default AddBlog