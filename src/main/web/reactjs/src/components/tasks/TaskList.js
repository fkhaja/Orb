import React from 'react';
import {findAllTasksForCard, saveTask, updateTask} from "../../util/RequestUtils";
import "./TaskList.css";
import "../Modal.css"
import ProgressBar from "react-bootstrap/ProgressBar";
import Task from "./Task";
import TaskForm from "./TaskForm";
import {Button} from "react-bootstrap";

export default class TaskList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            completed: 0,
            tasks: this.props.card.tasks,
            showInput: false
        };
        this.updateTaskList = this.updateTaskList.bind(this);
        this.handleTaskUpdate = this.handleTaskUpdate.bind(this);
        this.handleShowInputChange = this.handleShowInputChange.bind(this);
        this.handleTaskCreate = this.handleTaskCreate.bind(this);
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
                    <Task task={task} onUpdate={this.handleTaskUpdate} key={task.taskId}
                          cardId={this.props.card.cardId}/>
                ))}

                {this.state.showInput ?
                    <TaskForm onCancel={this.handleShowInputChange} onSubmit={this.handleTaskCreate}/>
                    :
                    <Button variant="outline-primary" onClick={this.handleShowInputChange} className="add_task__btn">
                        Add task
                    </Button>
                }

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
        this.setState({
            completed: this.state.tasks.filter(task => task.completed).length,
            showInput: false
        });
        updateTask(task, this.props.card.cardId).catch(console.log);
    }

    handleTaskCreate(task) {
        saveTask(task, this.props.card.cardId).then(this.updateTaskList).catch(console.log);
    }

    handleShowInputChange() {
        this.setState(() => ({showInput: !this.state.showInput}));
    }

    updateTaskList() {
        findAllTasksForCard(this.props.card.cardId).then(data => this.setState({tasks: data}));
    }

    getPercentage(number, count) {
        return ((number * 100) / count).toFixed(0);
    }
}