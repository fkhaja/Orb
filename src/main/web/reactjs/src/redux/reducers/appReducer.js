import {HIDE_LOADING_PAGE, SHOW_LOADING_PAGE} from "../types";

const initialState = {
    showLoadingPage: false
};

export const appReducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADING_PAGE:
            return {...state, showLoadingPage: true};
        case HIDE_LOADING_PAGE:
            return {...state, showLoadingPage: false};
        default:
            return state;
    }
};