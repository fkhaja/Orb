import React from 'react';
import {saveTask} from "../../util/RequestUtils";
import "./TaskList.css";
import "../Modal.css"
import {Button} from "react-bootstrap";
import Form from "react-bootstrap/Form";

export default class TaskForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            inputEnable: false,
            value: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleInputEnableChange = this.handleInputEnableChange.bind(this);
        this.clearInput = this.clearInput.bind(this);
    }

    render() {
        return (
            <div>
                {!this.state.inputEnable &&
                <Button variant="outline-primary"
                        onClick={this.handleInputEnableChange}
                        className="add_task__btn">Add task</Button>
                }
                {this.state.inputEnable &&
                <Form>
                    <Form.Group controlId="taskValue">
                        <Form.Control type="text" onChange={this.handleChange}
                                      value={this.state.value} placeholder="Enter task"/>
                    </Form.Group>
                    <Button variant="primary" type="submit" onClick={this.handleSubmit}>Save</Button>
                    {' '}
                    <Button variant="primary" onClick={this.handleInputEnableChange}>Cancel</Button>
                </Form>
                }
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
            this.setState({inputEnable: !this.state.inputEnable});
            saveTask(newTask, this.props.cardId).then(this.props.onTaskCreate).catch(console.log);
            this.clearInput();
        }
    }

    handleInputEnableChange() {
        this.setState(() => ({inputEnable: !this.state.inputEnable}));
    }

    clearInput() {
        this.setState(() => ({value: ''}));
    }
}