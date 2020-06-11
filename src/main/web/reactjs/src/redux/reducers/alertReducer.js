import {
    HIDE_COMPLETION_ERROR_ALERT,
    HIDE_COMPLETION_SUCCESS_ALERT,
    SHOW_COMPLETION_ERROR_ALERT,
    SHOW_COMPLETION_SUCCESS_ALERT
} from "../types";

const initialState = {
    showCompletionSuccessAlert: false,
    showCompletionErrorAlert: false,
};

export const alertReducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_COMPLETION_SUCCESS_ALERT:
            return {...state, showCompletionSuccessAlert: true};
        case HIDE_COMPLETION_SUCCESS_ALERT:
            return {...state, showCompletionSuccessAlert: false};
        case SHOW_COMPLETION_ERROR_ALERT:
            return {...state, showCompletionErrorAlert: true};
        case HIDE_COMPLETION_ERROR_ALERT:
            return {...state, showCompletionErrorAlert: false};
        default:
            return state;
    }
};