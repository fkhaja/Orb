import {
    HIDE_COMPLETION_ERROR_ALERT,
    HIDE_COMPLETION_SUCCESS_ALERT,
    SHOW_COMPLETION_ERROR_ALERT,
    SHOW_COMPLETION_SUCCESS_ALERT
} from "../types";

export function showCompletionSuccessAlert() {
    return {
        type: SHOW_COMPLETION_SUCCESS_ALERT
    };
}

export function hideCompletionSuccessAlert() {
    return {
        type: HIDE_COMPLETION_SUCCESS_ALERT
    };
}

export function showCompletionErrorAlert() {
    return {
        type: SHOW_COMPLETION_ERROR_ALERT
    };
}

export function hideCompletionErrorAlert() {
    return {
        type: HIDE_COMPLETION_ERROR_ALERT
    };
}