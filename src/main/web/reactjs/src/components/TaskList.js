import React from 'react';
import {getTasks, updateTasks} from "../util/APIUtils";
import "./TaskList.css";
import ProgressBar from "react-bootstrap/ProgressBar";
import Task from "./Task";

export default class TaskList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            completed: 0,
            tasks: [],
        };
        this.handleTaskListChange = this.handleTaskListChange.bind(this);
        this.onCompleted = this.onCompleted.bind(this);
    }

    componentDidMount() {
        this.findAllTasks();
    }

    componentWillUnmount() {
        updateTasks(this.diff(this.state.tasks, this.props.card.tasks), this.props.card.id).catch(e => console.log(e));
    }

    findAllTasks() {
        getTasks(this.props.card.id)
            .then(data => {
                this.setState(state => ({
                    tasks: data
                }));
            }).then(this.onCompleted).catch(e => console.log(e))
    }

    render() {
        let idCount = 0;
        let taskCount = this.state.tasks.length;
        let percentage = this.getPercentage(this.state.completed, taskCount);
        console.log(this.state.completed);

        return (
            <div className="workspace task-list-body">
                <h1 className="text-muted">Tasks</h1>
                <div id="checklist" className="task-list">
                    {this.state.tasks.map(task => (
                        <Task task={task} onCompleted={this.onCompleted} id={++idCount}/>
                    ))}
                </div>
                <br/>
                <h3 className="text-muted text-center">Completed</h3>
                <ProgressBar striped variant="success"
                             label={`${percentage}%`}
                             now={percentage}/>
            </div>
        )
    }

    handleTaskListChange() {
        this.findAllTasks();
    }

    onCompleted() {
        let count = 0;
        let checks = document.getElementsByName("check");
        for (let i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                count++;
            }
        }
        this.setState({completed: count});
    }

    getPercentage(number, count) {
        return ((number * 100) / count).toFixed(0);
    }

    diff(a, b) {
        return a.filter(function(i) {return b.indexOf(i) < 0;});
    };
}