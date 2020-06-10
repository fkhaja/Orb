import {removeTask, saveTask, updateTask} from "../../util/RequestUtils";
import {CANCEL_TASK_COMPLETION, COMPLETE_TASK, CREATE_TASK, DELETE_TASK, EDIT_TASK} from "../types";

export function createTask(task, cardId) {
    return async dispatch => {
        const response = await saveTask(task, cardId);
        dispatch({
            type: CREATE_TASK,
            payload: response,
            cardId
        });
    };
}

export function editTask(value, taskId, cardId) {
    return async dispatch => {
        await updateTask({value}, taskId, cardId);
        dispatch({
            type: EDIT_TASK,
            value,
            cardId,
            taskId
        });
    };
}

export function deleteTask(taskId, cardId) {
    return async dispatch => {
        await removeTask(taskId, cardId);
        dispatch({
            type: DELETE_TASK,
            cardId,
            taskId
        });
    };
}

export function completeTask(taskId, cardId) {
    return async dispatch => {
        await updateTask({completed: true}, taskId, cardId);
        dispatch({
            type: COMPLETE_TASK,
            cardId,
            taskId
        });
    };
}

export function cancelTaskCompletion(taskId, cardId) {
    return async dispatch => {
        await updateTask({completed: false}, taskId, cardId);
        dispatch({
            type: CANCEL_TASK_COMPLETION,
            cardId,
            taskId
        });
    };
}