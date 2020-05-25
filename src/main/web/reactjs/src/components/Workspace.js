import React from 'react';
import TaskCardList from "./cards/TaskCardList";
import {findTaskCards} from "../util/RequestUtils";
import './Workspace.css';

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
                <div className="workspace">
                    {this.state.taskCards.length > 0 && <TaskCardList cards={this.state.taskCards}/>}
                </div>
            </div>
        );
    }
};