import {LOAD_LOGGED_IN_USER, LOGOUT_USER} from "../types";

const initialState = {
    currentUser: null,
    authenticated: false
};

export const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case LOAD_LOGGED_IN_USER:
            return {...state, currentUser: action.payload.currentUser, authenticated: action.payload.authenticated};
        case LOGOUT_USER:
            return {...state, currentUser: action.payload.currentUser, authenticated: action.payload.authenticated};
        default:
            return state;
    }
};