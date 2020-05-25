import React from 'react';
import TaskCardList from "./cards/TaskCardList";
import {findTaskCards} from "../util/RequestUtils";
import './Workspace.css';
import timerPic from '../img/timer.png';
import Image from "react-bootstrap/Image";

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
            <div className="workspace-container">
                <div className="main-content">
                    {this.state.taskCards.length > 0 && <TaskCardList cards={this.state.taskCards}/>}
                </div>
                <div className="sidebar-container">
                    <div className="sidebar-part">
                        <h1 className="sidebar-part-header">Progress</h1>
                        <div className="picture-block">
                            <Image src={timerPic} className="sidebar-picture"/>
                            <div>
                                <span>Daily goal</span>
                                <span>[____0_of_5____]</span>
                            </div>
                        </div>
                        <p className="sidebar-description">Complete 5 tasks to close the daily goal</p>
                    </div>
                    <div className="sidebar-part">
                        <div className="news-board">
                            <h2>Some news</h2>
                        </div>
                    </div>
                    <div className="sidebar-part">
                        <h1 className="sidebar-part-header">Statistics</h1>
                        <p className="sidebar-description">Will be available in the future</p>
                    </div>
                </div>
            </div>
        );
    }
};