import React, {useState} from 'react';
import "./TaskList.css";
import "../../styles/Modal.css"
import "../../styles/InputText.css"
import {faCheck, faTimes} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useDispatch} from "react-redux";
import {editTask} from "../../redux/actions/taskActions";

const TaskEditForm = ({initialValue, onCancel, cardId, id}) => {
    const dispatch = useDispatch();
    const [value, setValue] = useState(initialValue);

    const handleValueChange = event => setValue(event.target.value);
    const handleSubmit = event => {
        event.preventDefault();

        dispatch(editTask(value, id, cardId))
        onCancel();
        setValue("");
    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <div>
                    <input type="text" onChange={handleValueChange} className="task-edit-input"
                           name="name" value={value} maxLength={255} placeholder="Enter task"/>
                    <button className="task-save-btn" type="submit">
                        <FontAwesomeIcon icon={faCheck} title="Save changes"/>
                    </button>
                    <button className="task-cancel-btn" onClick={onCancel}>
                        <FontAwesomeIcon icon={faTimes} title="Cancel"/>
                    </button>
                </div>
            </form>
        </div>
    )
}

export default TaskEditForm;