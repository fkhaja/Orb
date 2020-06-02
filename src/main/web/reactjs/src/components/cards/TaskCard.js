import React, {Fragment} from 'react';
import './TaskCard.css';
import '../Modal.css';
import TaskCardContent from "./TaskCardContent";
import TaskCardForm from "./TaskCardForm";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faEdit, faTrash} from "@fortawesome/free-solid-svg-icons";


export default class TaskCard extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            editable: false,
            name: this.props.card.name
        };
        this.onDelete = this.onDelete.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleEditableChange = this.handleEditableChange.bind(this);
        this.handleSaveEdit = this.handleSaveEdit.bind(this);
    }

    componentDidMount() {
        this.elements = [];
        this.elements.push(document.getElementById(`modal-overlay-container-${this.props.card.cardId}`));
        this.elements.push(document.getElementById(`modal-div-${this.props.card.cardId}`));
    }

    render() {
        return (
            <div>
                <div className="card p-card">
                    {this.state.editable ?
                        <TaskCardForm onCancel={this.handleEditableChange} initialValue={this.state.name}
                                      onSubmit={this.handleSaveEdit}/>
                        :
                        <Fragment>
                            <div onClick={this.handleShow}>
                                <h3 className="h3-card">{this.props.card.name}</h3>
                                <p className="small p-card">
                                    {this.props.card.description || "Card without description"}
                                </p>
                                <div className="go-corner">
                                    <div className="go-arrow">→</div>
                                </div>
                            </div>
                            <div className="card-icons__container">
                                <FontAwesomeIcon icon={faEdit} color="white" size="sm" title="Edit"
                                                 className="card-icon" onClick={this.handleEditableChange}/>
                                <FontAwesomeIcon icon={faTrash} color="white" size="sm" title="Remove"
                                                 className="card-icon" onClick={this.onDelete}/>
                            </div>
                        </Fragment>
                    }
                </div>
                <div id={`modal-overlay-container-${this.props.card.cardId}`} className="modal-overlay">
                    <div id={`modal-div-${this.props.card.cardId}`} className="modal">
                        <p className="close-modal" onClick={this.handleClose}>
                            <svg viewBox="0 0 20 20">
                                <path fill="#8e54e9"
                                      d="M15.898,4.045c-0.271-0.272-0.713-0.272-0.986,0l-4.71,4.711L5.493,4.045c-0.272-0.272-0.714-0.272-0.986,0s-0.272,0.714,0,0.986l4.709,4.711l-4.71,4.711c-0.272,0.271-0.272,0.713,0,0.986c0.136,0.136,0.314,0.203,0.492,0.203c0.179,0,0.357-0.067,0.493-0.203l4.711-4.711l4.71,4.711c0.137,0.136,0.314,0.203,0.494,0.203c0.178,0,0.355-0.067,0.492-0.203c0.273-0.273,0.273-0.715,0-0.986l-4.711-4.711l4.711-4.711C16.172,4.759,16.172,4.317,15.898,4.045z"/>
                            </svg>
                        </p>
                        <div className="modal-content">
                            <h2 className="title">{this.props.card.name}</h2>
                            <div className="modal_task_list">
                                <TaskCardContent card={this.props.card}/>
                            </div>
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

    handleEditableChange() {
        this.setState(() => ({editable: !this.state.editable}));
    }

    handleSaveEdit(card) {
        this.setState(() => ({name: card.name, editable: false}), () => {
            this.props.card.name = this.state.name;
            this.props.onUpdate(this.props.card);
        });
    }

    onDelete() {
        this.props.onDelete(this.props.index);
    }
}