import React from 'react';
import {findAllTasksForCard, updateTask} from "../../util/RequestUtils";
import "./TaskList.css";
import "../Modal.css"
import ProgressBar from "react-bootstrap/ProgressBar";
import Task from "./Task";
import TaskForm from "./TaskForm";

export default class TaskList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            completed: 0,
            tasks: this.props.card.tasks,
        };
        this.handleTaskUpdate = this.handleTaskUpdate.bind(this);
        this.updateTaskList = this.updateTaskList.bind(this);
    }

    componentDidMount() {
        this.setState({completed: this.state.tasks.filter(task => task.completed).length});
    }

    render() {
        let taskCount = this.state.tasks.length;
        let percentage = this.getPercentage(this.state.completed, taskCount);

        return (
            <div>
                <hr/>
                {this.state.tasks.map(task => (
                    <Task task={task} onCompleted={this.handleTaskUpdate} key={task.taskId}/>
                ))}

                <TaskForm onTaskCreate={this.updateTaskList} cardId={this.props.card.cardId}/>

                {taskCount > 0 &&
                <div className="modal_progress_bar">
                    <hr/>
                    <h3 className="text-muted text-center small">Completed</h3>
                    <ProgressBar label={`${percentage}%`} now={percentage}/>
                </div>}
            </div>
        )
    }

    handleTaskUpdate(task) {
        this.setState({completed: this.state.tasks.filter(task => task.completed).length});
        updateTask(task, this.props.card.cardId).catch(e => console.log(e));
    }

    updateTaskList() {
        findAllTasksForCard(this.props.card.cardId).then(data => this.setState({
            tasks: data
        }));
    }

    getPercentage(number, count) {
        return ((number * 100) / count).toFixed(0);
    }
}