import {combineReducers} from "redux";
import {userReducer} from "./userReducer";
import {cardReducer} from "./cardReducer";
import {modalReducer} from "./modalReducer";
import {authReducer} from "./authReducer";

export const rootReducer = combineReducers({
    user: userReducer,
    cards: cardReducer,
    modal: modalReducer,
    auth: authReducer
});