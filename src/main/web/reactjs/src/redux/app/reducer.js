import {HIDE_LOADER, HIDE_LOADING_PAGE, SHOW_LOADER, SHOW_LOADING_PAGE} from "./actionTypes";

const initialState = {
    showLoadingPage: false,
    showLoader: false
};

export const appReducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADING_PAGE:
            return {...state, showLoadingPage: true};
        case HIDE_LOADING_PAGE:
            return {...state, showLoadingPage: false};
        case SHOW_LOADER:
            return {...state, showLoader: true};
        case HIDE_LOADER:
            return {...state, showLoader: false};
        default:
            return state;
    }
};