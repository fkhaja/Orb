import React from 'react';
import TaskCardList from "./cards/TaskCardList";
import {getTaskCards} from "../util/APIUtils";
import './Workspace.css';
import SideBar from "./navbars/SideBar";
import Header from "./Header";

export default class Workspace extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            taskCards: []
        }
    }

    componentDidMount() {
        this.findTaskCards();
    }

    findTaskCards() {
        getTaskCards(this.props.currentUser.id).then(data => this.setState(state => ({
            taskCards: data
        })));
    }

    render() {
        return (
            <div className="grid-container">
                <SideBar className="grid-col-1"/>
                <div className="grid-col-2">
                    <Header currentUser={this.props.currentUser} onLogout={this.props.onLogout} title='My task cards'/> <br/>
                    <div className="workspace">
                        <TaskCardList cards={this.state.taskCards}/>
                    </div>
                </div>
            </div>
        );
    }
};