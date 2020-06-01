import React from 'react';
import TaskCardList from "./cards/TaskCardList";
import {findTaskCards} from "../util/RequestUtils";
import './Workspace.css';
import Sidebar from "./navbars/Sidebar";
import Header from "./navbars/Header";

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
        findTaskCards().then(data => this.setState(() => ({
            taskCards: data.content
        })));
    }

    render() {
        return (
            <div className="workspace-box">
                <Sidebar className="sidebar"/>
                <div className="workspace">
                    <Header currentUser={this.props.currentUser}/>
                    {this.state.taskCards.length > 0 && <TaskCardList cards={this.state.taskCards}/>}
                </div>
            </div>
        );
    }
};