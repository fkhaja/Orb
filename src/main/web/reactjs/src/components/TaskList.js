import React, {Fragment} from 'react';
import {getTasks} from "../util/APIUtils";
import "./TaskList.css";

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
        let count = 0;

        return (
            <div className="workspace task-list-body">
                <h1 className="text-muted">Tasks</h1>
                <div id="checklist" className="task-list">
                    {this.state.tasks.map(task => (
                        <Fragment key={task.id}>
                            <input id={++count} type="checkbox" name="r"/>
                            <label htmlFor={count}>{task.value}</label>
                        </Fragment>
                    ))}
                </div>
            </div>
        )
    }

    handleTaskListChange() {
        this.findAllTasks();
    }
}