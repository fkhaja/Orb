import React from 'react';
import './TaskCard.css'
import SideBar from "../navbars/SideBar";
import Header from "../Header";
import TaskList from "../TaskList";

export default class TaskCardContent extends React.Component {
    render() {
        return (
            <div className="grid-container">
                <SideBar className="grid-col-1"/>
                <div className="grid-col-2">
                    <Header currentUser={this.props.currentUser} onLogout={this.props.onLogout} title={this.props.location.state.card.name}/> <br/>
                    <TaskList card={this.props.location.state.card}/>
                </div>
            </div>
        )
    }
}