import React from 'react';
import './TaskCard.css'
import Header from "../navbars/Header";
import TaskList from "../tasks/TaskList";

export default class TaskCardContent extends React.Component {
    render() {
        return (
            <div>
                <Header currentUser={this.props.currentUser} onLogout={this.props.onLogout}
                        title={this.props.location.state.card.name}/>
                <TaskList card={this.props.location.state.card}/>
            </div>
        )
    }
}