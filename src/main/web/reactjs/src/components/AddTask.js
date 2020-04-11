import React from 'react';
import axios from 'axios';
import {Button, Form} from "react-bootstrap";

export default class AddTask extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: ''};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    addTask(task) {
        axios.post("http://localhost:8080/tasks", task).then(this.props.onTaskAdd).catch(e => console.log(e));
    }

    render() {
        return (
            <Form className="text-white" onSubmit={this.handleSubmit}>
                <Form.Group>
                    <Form.Label htmlFor="new-task">Add task</Form.Label>
                    <Form.Control required value={this.state.value}
                                  name="value"
                                  type="text"
                                  placeholder="Task"
                                  id="new-task"
                                  onChange={this.handleChange}/>
                </Form.Group>
                <Button variant="secondary" type="submit">
                    Save
                </Button>
            </Form>
        )
    }

    handleChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

    handleSubmit(e) {
        e.preventDefault();
        const newTask = {
            value: this.state.value,
        };
        this.addTask(newTask);
        this.clearInput();
    }

    clearInput() {
        this.setState(state => ({
            value: ''
        }));
    }
}