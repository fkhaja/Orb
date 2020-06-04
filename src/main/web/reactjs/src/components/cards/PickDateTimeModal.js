import React from "react";
import "./PickDateTimeModal.css";
import TimeContainer from "@y0c/react-datepicker/lib/components/TimeContainer";
import '../datepicker/calendar.scss';
import "../../styles/Modal.css";
import DatePicker from "@y0c/react-datepicker/lib/components/DatePicker";

export default class AddCardModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            date: this.props.term,
            time: {
                hours: this.props.term.getHours(),
                minutes: this.props.term.getMinutes()
            }
        }

        this.onTimeChange = this.onTimeChange.bind(this);
        this.onDateChange = this.onDateChange.bind(this);
        this.onSaveEdit = this.onSaveEdit.bind(this);
    }

    render() {
        return (
            <div>
                <div id="datetime-modal-overlay-container-0 active"
                     className={`datetime-modal-overlay ${this.props.show ? "active" : ""}`}>
                    <div id="datetime-modal-div-0" className={`datetime-modal ${this.props.show ? "active" : ""}`}>
                        <p className="datetime-close-modal small" onClick={this.props.onClose}>
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
                                                initialDate={this.state.date} onChange={this.onDateChange}/>
                                </div>
                                <div className="datetime-box-time">
                                    <h6 className="text-muted font-weight-bold">Time</h6>
                                    <TimeContainer hour={this.state.time.hours}
                                                   minute={this.state.time.minutes} onChange={this.onTimeChange}/>
                                </div>
                            </div>
                            <button className="bttn-dark datetime-save-btn" onClick={this.onSaveEdit}>Save</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    onDateChange(date) {
        this.setState({date: new Date(date)})
    }

    onTimeChange(hours, minutes) {
        this.setState({time: {hours, minutes}});
    }

    onSaveEdit() {
        this.props.onChangeTerm(new Date(this.state.date.setHours(this.state.time.hours, this.state.time.minutes)));
        this.props.onClose();
    }
}