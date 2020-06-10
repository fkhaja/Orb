import {HIDE_CREATE_MODAL, SHOW_CREATE_MODAL} from "../types";

const initialState = {
    showCreateModal: false,
    showContentModal: false,
    showDatepickerModal: false
};

export const modalReducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_CREATE_MODAL:
            return {...state, showCreateModal: true};
        case HIDE_CREATE_MODAL:
            return {...state, showCreateModal: false};
        default:
            return state;
    }
};