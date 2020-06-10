import React, {useState} from "react";
import "./PickDateTimeModal.css";
import TimeContainer from "@y0c/react-datepicker/lib/components/TimeContainer";
import '../datepicker/calendar.scss';
import "../../styles/Modal.css";
import DatePicker from "@y0c/react-datepicker/lib/components/DatePicker";
import {useDispatch} from "react-redux";
import {editCardTerm} from "../../redux/actions/cardActions";
import moment from "moment";

const PickDateTimeModal = ({card, show, onClose}) => {
    const dispatch = useDispatch();
    const term = card.term ? new Date(card.term) : "";
    const [date, setDate] = useState(term || "");
    const [time, setTime] = useState({
        hours: term ? term.getHours() : 0,
        minutes: term ? term.getMinutes() : 0
    })

    const handleDateChange = date => setDate(new Date(date));
    const handleTimeChange = (hours, minutes) => setTime({hours, minutes});

    const submitChanges = () => {
        if (date) {
            const resultDate = moment(new Date(
                date.getFullYear(),
                date.getMonth(),
                date.getDate(),
                time.hours,
                time.minutes)).format("YYYY-MM-DDTHH:mm");

            dispatch(editCardTerm(card.cardId, resultDate));
            onClose();
        }
    }

    return (
        <div>
            <div id="datetime-modal-overlay-container-0 active"
                 className={`datetime-modal-overlay ${show ? "active" : ""}`}>
                <div id="datetime-modal-div-0" className={`datetime-modal ${show ? "active" : ""}`}>
                    <p className="datetime-close-modal small" onClick={onClose}>
                        <svg viewBox="0 0 20 20">
                            <path fill="#8e54e9"
                                  d="M15.898,4.045c-0.271-0.272-0.713-0.272-0.986,0l-4.71,4.711L5.493,4.045c-0.272-0.272-0.714-0.272-0.986,0s-0.272,0.714,0,0.986l4.709,4.711l-4.71,4.711c-0.272,0.271-0.272,0.713,0,0.986c0.136,0.136,0.314,0.203,0.492,0.203c0.179,0,0.357-0.067,0.493-0.203l4.711-4.711l4.71,4.711c0.137,0.136,0.314,0.203,0.494,0.203c0.178,0,0.355-0.067,0.492-0.203c0.273-0.273,0.273-0.715,0-0.986l-4.711-4.711l4.711-4.711C16.172,4.759,16.172,4.317,15.898,4.045z"/>
                        </svg>
                    </p>
                    <div className="datetime-modal-content">
                        <h2 className="datetime-title">Changing the completion date</h2>
                        <hr/>
                        <div className="datetime-box">
                            <div className="datetime-box-date">
                                <h6 className="text-muted font-weight-bold">Date</h6>
                                <DatePicker locale="en" className="date-picker"
                                            initialDate={date} onChange={handleDateChange}/>
                            </div>
                            <div className="datetime-box-time">
                                <h6 className="text-muted font-weight-bold">Time</h6>
                                <TimeContainer hour={time.hours} minute={time.minutes} onChange={handleTimeChange}/>
                            </div>
                        </div>
                        <button className="bttn-dark datetime-save-btn" onClick={submitChanges}>Save</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default PickDateTimeModal;