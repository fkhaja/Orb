import React from 'react';
import axios from 'axios';
import ListGroup from "react-bootstrap/ListGroup";

export default class TaskList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            tasks: []
        }
    }

    componentDidMount() {
        this.findAllTasks();
    }

    findAllTasks() {
        axios.get('http://localhost:8080/tasks')
            .then(response => response.data)
            .then(data => {
                this.setState(state => ({
                    tasks: data
                }))
            })
    }

    render() {
        return (
            <div>
                <h1 className="text-white">Task List</h1>
                <ListGroup className="text-white">
                    {this.state.tasks.map(task => (
                        <ListGroup.Item className="bg-dark">#{task.id}: {task.value}</ListGroup.Item>
                    ))}
                </ListGroup>
            </div>
        )
    }
}