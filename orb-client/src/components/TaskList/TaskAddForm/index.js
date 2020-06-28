import React, {useState} from "react";
import "../style.css";
import "../../../styles/modal.css";
import "../../../styles/inputText.css";
import {faCheck, faTimes} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useDispatch} from "react-redux";
import {createTask} from "../../../redux/cards/actions";

const TaskAddForm = ({onCancel, cardId}) => {
    const dispatch = useDispatch();
    const [value, setValue] = useState("")

    const handleValueChange = event => setValue(event.target.value);
    const handleSubmit = event => {
        event.preventDefault();
        const newTask = {value: value};
        dispatch(createTask(newTask, cardId))
        setValue("");
    }

    return (
        <div className="task-add-form">
            <form onSubmit={handleSubmit}>
                <div>
                    <input type="text" onChange={handleValueChange} className="task-edit-input"
                           name="name" value={value} placeholder="Enter task" required maxLength={255}/>
                    <button className="task-save-btn" type="submit">
                        <FontAwesomeIcon icon={faCheck} title="Save"/>
                    </button>
                    <button className="task-cancel-btn" onClick={onCancel}>
                        <FontAwesomeIcon icon={faTimes} title="Cancel"/>
                    </button>
                </div>
            </form>
        </div>
    )
}

export default TaskAddForm;