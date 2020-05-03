import React from 'react';
import ListGroup from "react-bootstrap/ListGroup";
import {getTasks} from "../util/APIUtils";

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
        console.log(this.state.tasks);
    }

    findAllTasks() {
        getTasks(this.props.card.id)
            .then(data => {
                this.setState(state => ({
                    tasks: data
                }))
            }).catch(e => console.log(e))
    }

    render() {
        return (
            <div  className="workspace">
                <h1 className="text-muted">Task List</h1>
                <ListGroup variant="flush">
                    {this.state.tasks.map(task => (
                        <ListGroup.Item key={task.id}>
                            <span>#{task.id}: {task.value}</span>
                        </ListGroup.Item>
                    ))}
                </ListGroup>
            </div>
        )
    }

    handleTaskListChange() {
        this.findAllTasks();
    }
}