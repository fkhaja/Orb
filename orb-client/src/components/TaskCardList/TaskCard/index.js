import React, {useState} from "react";
import "./style.css";
import "../../../styles/modal.css";
import {useDispatch, useSelector} from "react-redux";
import {hideCompletionErrorAlert, hideCompletionSuccessAlert} from "../../../redux/alert/actions";
import TaskCardContent from "./TaskCardContent";

const TaskCard = ({card, index}) => {
    const dispatch = useDispatch();
    const [showModal, setShowModal] = useState(false);
    const done = useSelector(state => state.cards.fetchedCards.find(c => c.cardId === card.cardId).done);

    const handleModalClose = () => {
        setShowModal(false);
        setTimeout(() => {
            dispatch(hideCompletionErrorAlert());
            dispatch(hideCompletionSuccessAlert());
        }, 700);
    }

    return (
        <div>
            <div className="card p-card" onClick={() => setShowModal(true)}>
                <h3 className="h3-card">{card.title}</h3>
                <p className="small p-card">
                    {card.description || "No description"}
                </p>
                <div className="go-corner">
                    <div className="go-arrow">→</div>
                </div>
                <div className={`ribbon ${!done && "d-none"}`}>
                    <span>DONE</span>
                </div>
            </div>
            <div id={`modal-overlay-container-${index}`} className={`modal-overlay ${showModal && "active"}`}>
                <div id={`modal-div-${index}`} className={`modal ${showModal && "active"}`}>
                    <p className="close-modal small" onClick={handleModalClose}>
                        <svg viewBox="0 0 20 20">
                            <path fill="#8e54e9"
                                  d="M15.898,4.045c-0.271-0.272-0.713-0.272-0.986,0l-4.71,4.711L5.493,4.045c-0.272-0.272-0.714-0.272-0.986,0s-0.272,0.714,0,0.986l4.709,4.711l-4.71,4.711c-0.272,0.271-0.272,0.713,0,0.986c0.136,0.136,0.314,0.203,0.492,0.203c0.179,0,0.357-0.067,0.493-0.203l4.711-4.711l4.71,4.711c0.137,0.136,0.314,0.203,0.494,0.203c0.178,0,0.355-0.067,0.492-0.203c0.273-0.273,0.273-0.715,0-0.986l-4.711-4.711l4.711-4.711C16.172,4.759,16.172,4.317,15.898,4.045z"/>
                        </svg>
                    </p>
                    <div className="modal-content">
                        <TaskCardContent card={card}/>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default TaskCard;