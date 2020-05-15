import React, {Fragment} from 'react';
import './Task.css'

export default class Task extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            completed: this.props.task.completed,
            show: false
        };

        this.onCompleted = this.onCompleted.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleAccept = this.handleAccept.bind(this);
    }

    componentDidMount() {
        this.setState(() => ({
            completed: this.props.task.completed
        }));

        this.elements = document.querySelectorAll('.modal-overlay, .modal');
    }

    render() {
        return (
            <Fragment key={this.props.task.id}>
                <input id={this.props.id} type="checkbox" name="check"
                       onChange={this.handleShow}
                       checked={this.state.completed}/>
                <label htmlFor={this.props.id}>{this.props.task.value}</label>
                <div className="modal-overlay">
                    <div className="modal">
                        <p className="close-modal" onClick={this.handleClose}>
                            <svg viewBox="0 0 20 20">
                                <path fill="#8e54e9"
                                      d="M15.898,4.045c-0.271-0.272-0.713-0.272-0.986,0l-4.71,4.711L5.493,4.045c-0.272-0.272-0.714-0.272-0.986,0s-0.272,0.714,0,0.986l4.709,4.711l-4.71,4.711c-0.272,0.271-0.272,0.713,0,0.986c0.136,0.136,0.314,0.203,0.492,0.203c0.179,0,0.357-0.067,0.493-0.203l4.711-4.711l4.71,4.711c0.137,0.136,0.314,0.203,0.494,0.203c0.178,0,0.355-0.067,0.492-0.203c0.273-0.273,0.273-0.715,0-0.986l-4.711-4.711l4.711-4.711C16.172,4.759,16.172,4.317,15.898,4.045z"/>
                            </svg>
                        </p>
                        <div className="modal-content">
                            <h2 className="header">Assign as {this.state.completed ? "uncompleted" : "completed"}?</h2>
                            <p className="modal-content-text">
                                Click this button if you have {this.state.completed ? "not" : ""} completed the task
                            </p>
                            <div>
                                <div className="flex dark">
                                    <p onClick={this.handleAccept} className="bttn-dark">Yes</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </Fragment>
        )
    }

    onCompleted() {
        this.setState({completed: !this.state.completed}, () => {
            this.props.task.completed = this.state.completed;
            this.props.onCompleted(this.props.task);
        });
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

    handleAccept() {
        this.handleClose();
        this.onCompleted();
    }
}