import {deleteTaskCard, findTaskCards, saveTaskCard, updateTaskCard} from "../../util/RequestUtils";
import {COMPLETE_CARD, CREATE_CARD, DELETE_CARD, EDIT_CARD_DESCRIPTION, EDIT_CARD_TERM, FETCH_CARDS} from "../types";
import {showCompletionSuccessAlert} from "./alertActions";
import {hideLoader, showLoader} from "./appActions";

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
        await updateTaskCard(id, {description});
        dispatch({
            type: EDIT_CARD_DESCRIPTION,
            description: description,
            id
        });
    }
}

export function editCardTerm(id, term) {
    return async dispatch => {
        await updateTaskCard(id, {term});
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
        await updateTaskCard(id, {done: true});
        dispatch({
            type: COMPLETE_CARD,
            id
        });
    }
}