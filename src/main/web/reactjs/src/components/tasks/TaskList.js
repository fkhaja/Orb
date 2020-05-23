import React from 'react';
import {deleteTask, saveTask, updateTask} from "../../util/RequestUtils";
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
                <div className="task__container">
                    {this.state.tasks.map((task, i) => (
                        <Task task={task} onUpdate={this.handleTaskUpdate} key={task.taskId} index={i}
                              cardId={this.props.card.cardId} onDelete={(i) => this.handleTaskDelete(i)}/>
                    ))}

                    {this.state.showInput ?
                        <TaskForm onCancel={this.handleShowInputChange} onSubmit={this.handleTaskCreate}/>
                        :
                        <Button variant="outline-primary" onClick={this.handleShowInputChange} className="add__btn">
                            Add task
                        </Button>
                    }
                </div>
                {taskCount > 0 &&
                <div className="modal_progress_bar footer">
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