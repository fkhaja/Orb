import React from 'react';
import './TaskList.css';

export default class Task extends React.Component {

    constructor(props) {
        super(props);
        this.onCompleted = this.onCompleted.bind(this);
    }

    render() {
        const id = `option${this.props.task.taskId}`;
        return (
            <div className="inputGroup">
                <input id={id} name={id} type="checkbox" onChange={this.onCompleted} checked={this.props.task.completed}/>
                <label htmlFor={id}>
                    <span className="inputGroup_content">{this.props.task.value}</span>
                </label>
            </div>
        )
    }

    onCompleted() {
        this.props.task.completed = !this.props.task.completed;
        this.props.onCompleted(this.props.task);
    }
}