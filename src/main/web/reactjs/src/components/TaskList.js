import React from 'react';
import axios from 'axios';
import ListGroup from "react-bootstrap/ListGroup";
import AddTask from "./AddTask";

export default class TaskList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            tasks: [],
        };
        this.handleTaskListChange = this.handleTaskListChange.bind(this);
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
            }).catch(e => console.log(e))
    }

    render() {
        return (
            <div>
                <h1 className="text-white">Task List</h1>
                <AddTask onTaskAdd={this.handleTaskListChange}/> <br/>
                <div>
                    <ListGroup className="text-white">
                        {this.state.tasks.map(task => (
                            <ListGroup.Item className="bg-dark" key={task.id}>
                                #{task.id}: {task.value}
                            </ListGroup.Item>
                        ))}
                    </ListGroup>
                </div>
            </div>
        )
    }

    handleTaskListChange() {
        this.findAllTasks();
    }
}