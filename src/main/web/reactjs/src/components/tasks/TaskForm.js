import React from 'react';
import "./TaskList.css";
import "../../styles/Modal.css"
import {Button} from "react-bootstrap";
import Form from "react-bootstrap/Form";

export default class TaskForm extends React.Component {
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
                <Form>
                    <Form.Group controlId="taskValue">
                        <Form.Control type="text" onChange={this.handleChange}
                                      value={this.state.value} placeholder="Enter task"/>
                    </Form.Group>
                    <Button variant="primary" type="submit" onClick={this.handleSubmit}>Save</Button>
                    {' '}
                    <Button variant="primary" onClick={this.props.onCancel}>Cancel</Button>
                </Form>
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