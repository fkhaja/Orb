import {
    deleteTaskCard,
    findTaskCards,
    partlyUpdateTask,
    partlyUpdateTaskCard,
    removeTask,
    saveTask,
    saveTaskCard
} from "../../util/requestUtils";
import {
    CANCEL_CARD_TERM_COMPLETION,
    CANCEL_TASK_COMPLETION,
    COMPLETE_CARD,
    COMPLETE_CARD_TERM,
    COMPLETE_TASK,
    CREATE_CARD,
    CREATE_TASK,
    DELETE_CARD,
    DELETE_TASK,
    EDIT_CARD_DESCRIPTION,
    EDIT_CARD_TERM,
    EDIT_TASK,
    FETCH_CARDS
} from "./actionTypes";
import {showCompletionSuccessAlert} from "../alert/actions";
import {hideLoader, showLoader} from "../app/actions";

export function fetchCards(page, size) {
    return async dispatch => {
        dispatch(showLoader());
        const response = await findTaskCards(page, size);
        dispatch({
            type: FETCH_CARDS,
            payload: response.content,
            totalPages: response.totalPages
        });
        dispatch(hideLoader());
    };
}

export function createCard(card) {
    return async dispatch => {
        const response = await saveTaskCard(card);
        dispatch({
            type: CREATE_CARD,
            payload: response
        });
    };
}

export function deleteCard(id) {
    return async dispatch => {
        await deleteTaskCard(id);
        dispatch({
            type: DELETE_CARD,
            id: id
        });
    };
}

export function editCardDescription(id, description) {
    return async dispatch => {
        await partlyUpdateTaskCard(id, {description});
        dispatch({
            type: EDIT_CARD_DESCRIPTION,
            description: description,
            id
        });
    }
}

export function editCardTerm(id, term) {
    return async dispatch => {
        await partlyUpdateTaskCard(id, {term});
        dispatch({
            type: EDIT_CARD_TERM,
            term,
            id
        });
    }
}

export function completeCard(id) {
    return async dispatch => {
        dispatch(showCompletionSuccessAlert());
        await partlyUpdateTaskCard(id, {done: true});
        dispatch({
            type: COMPLETE_CARD,
            id
        });
    }
}

export function completeCardTerm(id) {
    return async dispatch => {
        await partlyUpdateTaskCard(id, {completedAtTerm: true});
        dispatch({
            type: COMPLETE_CARD_TERM,
            id
        });
    }
}

export function cancelCardTermCompletion(id) {
    return async dispatch => {
        await partlyUpdateTaskCard(id, {completedAtTerm: false});
        dispatch({
            type: CANCEL_CARD_TERM_COMPLETION,
            id
        });
    }
}

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
        await partlyUpdateTask({value}, taskId, cardId);
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
        await partlyUpdateTask({completed: true}, taskId, cardId);
        dispatch({
            type: COMPLETE_TASK,
            cardId,
            taskId
        });
    };
}

export function cancelTaskCompletion(taskId, cardId) {
    return async dispatch => {
        await partlyUpdateTask({completed: false}, taskId, cardId);
        dispatch({
            type: CANCEL_TASK_COMPLETION,
            cardId,
            taskId
        });
    };
}