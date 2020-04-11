import React from 'react';
import {Button} from "react-bootstrap";
import axios from "axios";

export default class DeleteTask extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    render() {
        return (
            <Button variant="danger" onClick={this.handleChange} size="sm">Delete</Button>
        )
    }

    deleteTask(id) {
        axios.delete(`http://localhost:8080/tasks/${id}`).then(this.props.onTaskDelete).catch(e => console.log(e));
    }

    handleChange() {
        this.deleteTask(this.props.taskId);
    }
}