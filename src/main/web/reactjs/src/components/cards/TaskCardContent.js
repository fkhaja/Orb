import React, {useState} from 'react';
import TaskList from "../tasks/TaskList";
import "./TaskCardContent.css";
import EdiText from 'react-editext';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faCalendarAlt,
    faCheck,
    faCheckCircle,
    faCopy,
    faInfoCircle,
    faTimes,
    faTrash,
    faWrench
} from "@fortawesome/free-solid-svg-icons";
import {useDispatch} from "react-redux";
import {deleteCard, editCardDescription} from "../../redux/actions/cardActions";
import moment from "moment";
import PickDateTimeModal from "./PickDateTimeModal";

const TaskCardContent = ({card}) => {
    const dispatch = useDispatch();
    const [show, setShow] = useState(false);

    const handleDescriptionUpdate = description => dispatch(editCardDescription(card.cardId, description));

    const getCardStatus = (term) => {
        const completedTaskNumber = card.tasks.filter(task => task.completed).length;
        const today = moment(Date.now()).format("YYYY-MM-DDTHH:mm");

        if(completedTaskNumber < card.tasks.length && today > term) return "fail";

        return completedTaskNumber === card.tasks.length && today <= term ? "success" : "in-progress";
    }

    return (
        <div>
            <h3 className="card-name">{card.name}</h3>
            <div className="card-term">
                <h6 className="text-muted font-weight-bold small text-uppercase">
                    <FontAwesomeIcon icon={faCalendarAlt}/>
                    <span className="text">Term</span>
                </h6>
                <div onClick={() => setShow(true)} className="term-box">
                        <span className="term-date">
                            {card.term ? moment(card.term).format("MMMM D, HH:mm") : "No time limit"}
                        </span>
                    <span className={`term-status ${getCardStatus(card.term)}`}>{getCardStatus(card.term)}</span>
                </div>
            </div>
            <div className="card-description">
                <h6 className="text-muted font-weight-bold small text-uppercase">
                    <FontAwesomeIcon icon={faInfoCircle}/>
                    <span>Description</span>
                </h6>
                <EdiText className="text-muted"
                         mainContainerClassName="main-text-container"
                         editContainerClassName="edit-text-container"
                         editButtonClassName="edit-btn"
                         saveButtonClassName="save-btn"
                         cancelButtonClassName="cancel-btn"
                         cancelButtonContent={<FontAwesomeIcon icon={faTimes} title="Cancel"/>}
                         saveButtonContent={<FontAwesomeIcon icon={faCheck} title="Save changes"/>}
                         type="text"
                         value={card.description || "No description."}
                         onSave={handleDescriptionUpdate}
                         editOnViewClick/>
            </div>
            <div className="card-tasks-title">
                <h6 className="text-muted font-weight-bold small text-uppercase">
                    <FontAwesomeIcon icon={faCheckCircle}/>
                    <span>Tasks</span>
                </h6>
            </div>

            <TaskList card={card}/>

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
                    <button className="button-remove" onClick={() => dispatch(deleteCard(card.cardId))}>
                        <FontAwesomeIcon icon={faTrash}/>
                        <span>Remove</span>
                    </button>
                </div>
            </div>
            <PickDateTimeModal show={show} onClose={() => setShow(false)} card={card}/>
        </div>
    );
}

export default TaskCardContent;