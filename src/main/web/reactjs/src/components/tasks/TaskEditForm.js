import React from 'react';
import "./TaskList.css";
import "../../styles/Modal.css"
import "../../styles/InputText.css"
import {faCheck, faTimes} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

export default class TaskEditForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: this.props.initialValue || '',
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.clearInput = this.clearInput.bind(this);
    }

    render() {
        return (
            <div>
                <form>
                    <div>
                        <input type="text" onChange={this.handleChange} className="task-edit-input"
                               name="name" value={this.state.value}/>
                        <button className="task-save-btn" onClick={this.handleSubmit}>
                            <FontAwesomeIcon icon={faCheck} title="Save changes"/>
                        </button>
                        <button className="task-cancel-btn" onClick={this.props.onCancel}>
                            <FontAwesomeIcon icon={faTimes} title="Cancel"/>
                        </button>
                    </div>
                </form>
            </div>
        )
    }

    handleChange(e) {
        this.setState({value: e.target.value});
    }

    handleSubmit(e) {
        e.preventDefault();
        if (this.state.value.length !== 0) {
            const newTask = {value: this.state.value};
            this.props.onSubmit(newTask);
            this.clearInput();
        }
    }

    clearInput() {
        this.setState(() => ({value: ''}));
    }
}