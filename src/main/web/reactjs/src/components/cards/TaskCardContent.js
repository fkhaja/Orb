import React from 'react';
import TaskList from "../tasks/TaskList";

export default class TaskCardContent extends React.Component {

    render() {
        return (
            <div>
                <TaskList card={this.props.card}/>
            </div>
        )
    }
}