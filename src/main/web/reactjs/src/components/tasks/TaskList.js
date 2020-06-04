import React from 'react';
import {deleteTask, saveTask, updateTask} from "../../util/RequestUtils";
import "./TaskList.css";
import "../../styles/Modal.css"
import ProgressBar from "react-bootstrap/ProgressBar";
import Task from "./Task";
import TaskEditForm from "./TaskEditForm";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus} from "@fortawesome/free-solid-svg-icons";

export default class TaskList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            completed: 0,
            tasks: this.props.card.tasks,
            showInput: false
        };
        this.handleTaskUpdate = this.handleTaskUpdate.bind(this);
        this.handleShowInputChange = this.handleShowInputChange.bind(this);
        this.handleTaskCreate = this.handleTaskCreate.bind(this);
        this.handleTaskDelete = this.handleTaskDelete.bind(this);
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
                {taskCount > 0 &&
                <div className="modal-progress-bar">
                    <ProgressBar label={`${percentage}%`} now={percentage} variant="success"/>
                </div>}
                <div className="task-container">
                    {this.state.tasks.map((task, i) => (
                        <Task task={task} onUpdate={this.handleTaskUpdate} key={task.taskId} index={i}
                              cardId={this.props.card.cardId} onDelete={(i) => this.handleTaskDelete(i)}/>
                    ))}

                    {this.state.showInput ?
                        <TaskEditForm onCancel={this.handleShowInputChange} onSubmit={this.handleTaskCreate}/>
                        :
                        <div className="add-task" onClick={this.handleShowInputChange}>
                            <label>
                            <span className="add-task-icon-container">
                                <FontAwesomeIcon icon={faPlus} className="add-task-icon"/>
                            </span>
                                <span className="add-task-content">Add task</span>
                            </label>
                        </div>
                    }
                </div>
                <hr/>
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
        saveTask(task, this.props.card.cardId)
            .then(response => this.setState(() => ({tasks: this.state.tasks.concat(response)})))
            .catch(console.log);
    }

    handleTaskDelete(index) {
        deleteTask(this.state.tasks[index], this.props.card.cardId).catch(console.log);
        let newTasks = this.state.tasks.slice();
        newTasks.splice(index, 1);
        this.setState(() => ({tasks: newTasks}));
    }

    handleShowInputChange() {
        this.setState(() => ({showInput: !this.state.showInput}));
    }

    getPercentage(number, count) {
        return ((number * 100) / count).toFixed(0);
    }
}