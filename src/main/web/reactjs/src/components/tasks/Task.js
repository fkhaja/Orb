import React, {Fragment} from 'react';
import './TaskList.css';
import TaskEditForm from "./TaskEditForm";
import {faEdit, faTrash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

export default class Task extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            editable: false,
            value: this.props.task.value
        };
        this.onCompleted = this.onCompleted.bind(this);
        this.onDelete = this.onDelete.bind(this);
        this.handleEditableChange = this.handleEditableChange.bind(this);
        this.handleSaveEdit = this.handleSaveEdit.bind(this);
    }

    render() {
        const id = `option${this.props.task.taskId}`;
        return (
            <Fragment>
                {this.state.editable ?
                    <TaskEditForm initialValue={this.state.value} onCancel={this.handleEditableChange}
                                  onSubmit={this.handleSaveEdit}/>
                    :
                    <div className="inputGroup">
                        <input id={id} name={id} type="checkbox" onChange={this.onCompleted}
                               checked={this.props.task.completed}/>
                        <label htmlFor={id}>
                            <span className="inputGroup_content">{this.state.value}</span>
                            <div className={`action_buttons ${this.props.task.completed && "hidden"}`}>
                                <FontAwesomeIcon icon={faEdit} color="gray" size="sm" title="Edit"
                                                 className="card-icon" onClick={this.handleEditableChange}/>
                                <FontAwesomeIcon icon={faTrash} color="gray" size="sm" title="Remove"
                                                 className="card-icon" onClick={this.onDelete}/>
                            </div>
                        </label>
                    </div>
                }
            </Fragment>

        )
    }

    handleEditableChange() {
        this.setState(() => ({editable: !this.state.editable}))
    }

    handleSaveEdit(task) {
        this.setState(() => ({value: task.value, editable: false}), () => {
            this.props.task.value = this.state.value;
            this.props.onUpdate(this.props.task);
        });
    }

    onDelete() {
        this.props.onDelete(this.props.index);
    }

    onCompleted() {
        this.props.task.completed = !this.props.task.completed;
        this.props.onUpdate(this.props.task);
    }
}