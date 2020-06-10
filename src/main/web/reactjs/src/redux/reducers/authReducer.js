import {LOG_IN, SIGN_UP} from "../types";

const initialState = {
    accessToken: ""
}

export const authReducer = (state = initialState, action) => {
    switch (action.type) {
        case LOG_IN:
            return state;
        case SIGN_UP:
            return state;
        default:
            return state;
    }
};