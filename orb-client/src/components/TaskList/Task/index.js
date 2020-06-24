import React, {useState} from "react";
import "../style.css";
import {faEdit, faTrash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useDispatch} from "react-redux";
import {cancelTaskCompletion, completeTask, deleteTask} from "../../../redux/cards/actions";
import TaskEditForm from "./TaskEditForm";

const Task = ({task, cardId}) => {
    const dispatch = useDispatch();
    const [editable, setEditable] = useState(false);
    const [completed, setCompleted] = useState(task.completed)

    const handleCompletionChange = () => {
        if (completed) {
            setCompleted(false);
            dispatch(cancelTaskCompletion(task.taskId, cardId));
        } else {
            setCompleted(true);
            dispatch(completeTask(task.taskId, cardId));
        }
    }

    return (editable ?
            <TaskEditForm initialValue={task.value} cardId={cardId} id={task.taskId}
                          onCancel={() => setEditable(false)}/>
            :
            <div className="inputGroup">
                <input id={task.taskId} name={task.taskId} type="checkbox"
                       checked={completed}
                       onChange={handleCompletionChange}/>
                <label htmlFor={task.taskId} className="action-label">
                    <span className="inputGroup_content">{task.value}</span>
                    <div className={`action_buttons ${task.completed && "hidden"}`}>
                        <FontAwesomeIcon icon={faEdit} color="gray" size="sm" title="Edit"
                                         className="card-icon" onClick={() => setEditable(true)}/>
                        <FontAwesomeIcon icon={faTrash} color="gray" size="sm" title="Remove" className="card-icon"
                                         onClick={() => dispatch(deleteTask(task.taskId, cardId))}/>
                    </div>
                </label>
            </div>
    )
}

export default Task;