import React from 'react';
import {updateTask} from "../../util/RequestUtils";
import "./TaskList.css";
import ProgressBar from "react-bootstrap/ProgressBar";
import Task from "./Task";

export default class TaskList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            completed: 0,
            tasks: this.props.card.tasks,
        };
        this.handleTaskUpdate = this.handleTaskUpdate.bind(this);
    }

    componentDidMount() {
        this.setState({completed: this.state.tasks.filter(task => task.completed).length});
    }

    render() {
        let id = 0;
        let taskCount = this.state.tasks.length;
        let percentage = this.getPercentage(this.state.completed, taskCount);

        return (
            <div className="workspace task-list-body">
                <h1 className="text-muted">Tasks</h1>
                <div id="checklist" className="task-list">
                    {this.state.tasks.map(task => (
                        <Task task={task} onCompleted={this.handleTaskUpdate} id={++id} key={task.taskId}/>
                    ))}
                </div>
                <br/>
                {taskCount > 0 &&
                <div>
                    <h3 className="text-muted text-center">Completed</h3>
                    <ProgressBar striped variant="success"
                                 label={`${percentage}%`}
                                 now={percentage}/>
                </div>
                }
            </div>
        )
    }

    handleTaskUpdate(task) {
        this.setState({completed: this.state.tasks.filter(task => task.completed).length});
        updateTask(task, this.props.card.cardId).catch(e => console.log(e));
    }

    getPercentage(number, count) {
        return ((number * 100) / count).toFixed(0);
    }
}