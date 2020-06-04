import React from 'react';
import TaskList from "../tasks/TaskList";
import "./TaskCardContent.css";
import moment from "moment";
import PickDateTimeModal from "./PickDateTimeModal";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCheckCircle} from "@fortawesome/free-solid-svg-icons/faCheckCircle";
import {faCalendarAlt, faCheck, faCopy, faInfoCircle, faTrash, faWrench} from "@fortawesome/free-solid-svg-icons";

export default class TaskCardContent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {showModal: false};
    }

    render() {
        return (
            <div>
                <h3 className="card-name">{this.props.card.name}</h3>
                <div className="card-term">
                    <h6 className="text-muted font-weight-bold small text-uppercase">
                        <FontAwesomeIcon icon={faCalendarAlt}/>
                        <span className="text">Term</span>
                    </h6>
                    <div onClick={() => this.setState({show: !this.state.show})} className="term-box">
                        <span className="term-date">
                            {this.props.card.term ? moment(this.props.card.term).format("dddd MM, HH:mm") : "No time limit"}
                        </span>
                        <span className="term-status">Success</span>
                    </div>
                </div>
                <div className="card-description">
                    <h6 className="text-muted font-weight-bold small text-uppercase">
                        <FontAwesomeIcon icon={faInfoCircle}/>
                        <span>Description</span>
                    </h6>
                    <p className="text-muted">{this.props.card.description || "No description."}</p>
                </div>
                <div className="card-tasks-title">
                    <h6 className="text-muted font-weight-bold small text-uppercase">
                        <FontAwesomeIcon icon={faCheckCircle}/>
                        <span>Tasks</span>
                    </h6>
                </div>

                <TaskList card={this.props.card}/>

                <div className="card-actions">
                    <h6 className="text-muted font-weight-bold small text-uppercase">
                        <FontAwesomeIcon icon={faWrench}/>
                        <span>Actions</span>
                    </h6>
                    <div className="button-box">
                        <button className="button-copy">
                            <FontAwesomeIcon icon={faCopy}/>
                            <span>Copy</span>
                        </button>
                        <button className="button-mark-done">
                            <FontAwesomeIcon icon={faCheck}/>
                            <span>Mark as done</span>
                        </button>
                        <button className="button-remove">
                            <FontAwesomeIcon icon={faTrash}/>
                            <span>Remove</span>
                        </button>
                    </div>
                </div>

                <PickDateTimeModal show={this.state.show} onClose={() => this.setState({show: !this.state.show})}/>
            </div>
        );
    }
}