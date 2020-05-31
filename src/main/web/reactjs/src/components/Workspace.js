import React from 'react';
import TaskCardList from "./cards/TaskCardList";
import {findTaskCards} from "../util/RequestUtils";
import './Workspace.css';
import NavBar from "./navbars/NavBar";

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
                <NavBar className="sidebar"/>
                <div className="workspace">
                    {this.state.taskCards.length > 0 && <TaskCardList cards={this.state.taskCards}/>}
                </div>
            </div>
        );
    }
};