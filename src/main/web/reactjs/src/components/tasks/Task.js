import React, {useState} from 'react';
import './TaskList.css';
import TaskEditForm from "./TaskEditForm";
import {faEdit, faTrash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useDispatch} from "react-redux";
import {cancelTaskCompletion, completeTask, deleteTask} from "../../redux/actions/taskActions";

const Task = ({task, cardId}) => {
    const dispatch = useDispatch();
    const [editable, setEditable] = useState(false);
    const id = `option${task.taskId}`;
    let removed = false;

    const handleCompletionChange = () => {
        if (!removed) {
            dispatch(task.completed ? cancelTaskCompletion(task.taskId, cardId) : completeTask(task.taskId, cardId))
        }
    }

    const handleTaskDelete = () => {
        removed = true;
        dispatch(deleteTask(task.taskId, cardId));
    }

    return (editable ?
            <TaskEditForm initialValue={task.value} cardId={cardId} id={task.taskId}
                          onCancel={() => setEditable(false)}/> :
            <div className="inputGroup">
                <input id={id} name={id} type="checkbox"
                       checked={task.completed}
                       onChange={handleCompletionChange}/>
                <label htmlFor={id} className="action-label">
                    <span className="inputGroup_content">{task.value}</span>
                    <div className={`action_buttons ${task.completed && "hidden"}`}>
                        <FontAwesomeIcon icon={faEdit} color="gray" size="sm" title="Edit"
                                         className="card-icon" onClick={() => setEditable(true)}/>
                        <FontAwesomeIcon icon={faTrash} color="gray" size="sm" title="Remove" className="card-icon"
                                         onClick={handleTaskDelete}/>
                    </div>
                </label>
            </div>
    )
}

export default Task;