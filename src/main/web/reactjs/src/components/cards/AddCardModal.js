import React from "react";
import "../../styles/Modal.css";
import "../../styles/InputText.css";

export default class AddCardModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            description: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.clearInput = this.clearInput.bind(this);
    }

    render() {
        return (
            <div id="modal-overlay-container-0 active" className={`modal-overlay ${this.props.show ? "active" : ""}`}>
                <div id="modal-div-0" className={`modal ${this.props.show ? "active" : ""}`}>
                    <p className="close-modal" onClick={this.props.onClose}>
                        <svg viewBox="0 0 20 20">
                            <path fill="#8e54e9"
                                  d="M15.898,4.045c-0.271-0.272-0.713-0.272-0.986,0l-4.71,4.711L5.493,4.045c-0.272-0.272-0.714-0.272-0.986,0s-0.272,0.714,0,0.986l4.709,4.711l-4.71,4.711c-0.272,0.271-0.272,0.713,0,0.986c0.136,0.136,0.314,0.203,0.492,0.203c0.179,0,0.357-0.067,0.493-0.203l4.711-4.711l4.71,4.711c0.137,0.136,0.314,0.203,0.494,0.203c0.178,0,0.355-0.067,0.492-0.203c0.273-0.273,0.273-0.715,0-0.986l-4.711-4.711l4.711-4.711C16.172,4.759,16.172,4.317,15.898,4.045z"/>
                        </svg>
                    </p>
                    <div className="modal-content">
                        <h2 className="title">Create task card</h2>
                        <div className="modal_task_list">
                            <div>
                                <div className="input-wrapper">
                                    <form>
                                        <div className="group">
                                            <input type="text" required="required" onChange={this.handleChange}
                                                   name="name" value={this.state.name}/>
                                            <span className="highlight"/>
                                            <span className="bar"/>
                                            <label>Name</label>
                                        </div>
                                        <div className="group">
                                            <input type="text" required="required" onChange={this.handleChange}
                                                   name="description" value={this.state.description}/>
                                            <span className="highlight"/>
                                            <span className="bar"/>
                                            <label>Description</label>
                                        </div>
                                        <button className="bttn-dark sumbit" onClick={this.handleSubmit}>
                                            Create
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    handleChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

    handleSubmit(e) {
        e.preventDefault();
        if (this.state.name.length !== 0) {
            const newCard = {
                name: this.state.name,
                description: this.state.description,
            };
            this.props.onCreate(newCard);
            this.props.onClose();
            this.clearInput();
        }
    }

    clearInput() {
        this.setState(() => ({
            name: '',
            description: ''
        }));
    }
}