import React from 'react';
import TaskList from "../tasks/TaskList";

export default class TaskCardContent extends React.Component {

    render() {
        return (
            <div>
                <p className="text-muted text-md-center">Tasks</p>
                <TaskList card={this.props.card}/>
            </div>
        )
    }
}