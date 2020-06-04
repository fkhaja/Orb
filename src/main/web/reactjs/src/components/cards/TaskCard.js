import React from 'react';
import './TaskCard.css';
import '../../styles/Modal.css';
import TaskCardContent from "./TaskCardContent";

export default class TaskCard extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: this.props.card.name
        };
        this.handleClose = this.handleClose.bind(this);
        this.handleShow = this.handleShow.bind(this);
    }

    componentDidMount() {
        this.elements = [];
        this.elements.push(document.getElementById(`modal-overlay-container-${this.props.card.cardId}`));
        this.elements.push(document.getElementById(`modal-div-${this.props.card.cardId}`));
    }

    render() {
        return (
            <div>
                <div className="card p-card" onClick={this.handleShow}>
                    <h3 className="h3-card">{this.props.card.name}</h3>
                    <p className="small p-card">
                        {this.props.card.description || "Card without description"}
                    </p>
                    <div className="go-corner">
                        <div className="go-arrow">→</div>
                    </div>
                </div>
                <div id={`modal-overlay-container-${this.props.card.cardId}`} className="modal-overlay">
                    <div id={`modal-div-${this.props.card.cardId}`} className="modal">
                        <p className="close-modal small" onClick={this.handleClose}>
                            <svg viewBox="0 0 20 20">
                                <path fill="#8e54e9"
                                      d="M15.898,4.045c-0.271-0.272-0.713-0.272-0.986,0l-4.71,4.711L5.493,4.045c-0.272-0.272-0.714-0.272-0.986,0s-0.272,0.714,0,0.986l4.709,4.711l-4.71,4.711c-0.272,0.271-0.272,0.713,0,0.986c0.136,0.136,0.314,0.203,0.492,0.203c0.179,0,0.357-0.067,0.493-0.203l4.711-4.711l4.71,4.711c0.137,0.136,0.314,0.203,0.494,0.203c0.178,0,0.355-0.067,0.492-0.203c0.273-0.273,0.273-0.715,0-0.986l-4.711-4.711l4.711-4.711C16.172,4.759,16.172,4.317,15.898,4.045z"/>
                            </svg>
                        </p>
                        <div className="modal-content">
                            <TaskCardContent card={this.props.card}/>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    handleClose() {
        this.elements.forEach(function (item) {
            item.classList.remove('active');
        });
    }

    handleShow() {
        this.elements.forEach(function (item) {
            item.classList.add('active');
        });
    }
}