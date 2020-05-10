import React from 'react';
import TaskCardList from "./cards/TaskCardList";
import {findTaskCards} from "../util/RequestUtils";
import './Workspace.css';
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
            taskCards: data
        })));
    }

    render() {
        return (
            <div>
                <Header currentUser={this.props.currentUser} onLogout={this.props.onLogout} title='My task cards'/>
                <div className="workspace">
                    <TaskCardList cards={this.state.taskCards}/>
                </div>
            </div>
        );
    }
};