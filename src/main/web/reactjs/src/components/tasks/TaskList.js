import React, {useState} from 'react';
import "./TaskList.css";
import "../../styles/Modal.css"
import ProgressBar from "react-bootstrap/ProgressBar";
import Task from "./Task";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus} from "@fortawesome/free-solid-svg-icons";
import TaskAddForm from "./TaskAddForm";

const TaskList = ({card, done}) => {
    const [showInput, setShowInput] = useState(false);
    const tasks = card.tasks;
    const completed = tasks.filter(task => task.completed).length;

    const getPercentage = (number, count) => {
        return ((number * 100) / count).toFixed(0);
    }

    const percentage = getPercentage(completed, tasks.length);

    return (
        <div>
            <hr/>

            {tasks.length > 0 &&
            <div className="modal-progress-bar">
                <ProgressBar label={`${percentage}%`} now={percentage} variant="success"/>
            </div>}

            <div className="task-container">
                {card.tasks.map((task) => (
                    <Task task={task} key={task.taskId} cardId={card.cardId}/>
                ))}

                {!done && (showInput ? <TaskAddForm onCancel={() => setShowInput(false)} cardId={card.cardId}/> :
                    <div className="add-task" onClick={() => setShowInput(true)}>
                        <label>
                            <span className="add-task-icon-container">
                                <FontAwesomeIcon icon={faPlus} className="add-task-icon"/>
                            </span>
                            <span className="add-task-content">Add task</span>
                        </label>
                    </div>
                )}
            </div>
            <hr/>
        </div>
    )
}

export default TaskList;