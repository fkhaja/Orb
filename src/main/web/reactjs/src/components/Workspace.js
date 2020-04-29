import React from 'react';
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import TaskCardList from "./cards/TaskCardList";
import {getTaskCards} from "../util/APIUtils";
import SideBar from "./navbars/SideBar";

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
            <div>
                <SideBar/>
                <div style={{"margin": "0 auto", "width": "70%"}}>
                    <Tabs defaultActiveKey="profile" id="uncontrolled-tab-example" className="border">
                        <Tab eventKey="home" title="All Cards">
                            <div className="border-left border-right border-bottom">
                                <TaskCardList cards={this.state.taskCards}/>
                            </div>
                        </Tab>
                    </Tabs>
                </div>
            </div>
        );
    }
};