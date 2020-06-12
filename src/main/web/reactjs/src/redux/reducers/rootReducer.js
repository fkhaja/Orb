import {combineReducers} from "redux";
import {userReducer} from "./userReducer";
import {cardReducer} from "./cardReducer";
import {modalReducer} from "./modalReducer";
import {authReducer} from "./authReducer";
import {alertReducer} from "./alertReducer";
import {appReducer} from "./appReducer";

export const rootReducer = combineReducers({
    user: userReducer,
    cards: cardReducer,
    modal: modalReducer,
    auth: authReducer,
    alert: alertReducer,
    app: appReducer
});