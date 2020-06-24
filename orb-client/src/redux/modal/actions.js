import {HIDE_CREATE_MODAL, SHOW_CREATE_MODAL} from "./actionTypes";

export function showCreateModal() {
    return {
        type: SHOW_CREATE_MODAL
    };
}

export function hideCreateModal() {
    return {
        type: HIDE_CREATE_MODAL
    };
}