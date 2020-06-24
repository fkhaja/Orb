import {combineReducers} from "redux";
import {userReducer} from "./user/reducer";
import {cardsReducer} from "./cards/reducer";
import {modalReducer} from "./modal/reducer";
import {authReducer} from "./auth/reducer";
import {alertReducer} from "./alert/reducer";
import {appReducer} from "./app/reducer";
import {filtersReducer} from "./filters/reducer";

export const reducers = combineReducers({
    user: userReducer,
    cards: cardsReducer,
    modal: modalReducer,
    auth: authReducer,
    alert: alertReducer,
    filters: filtersReducer,
    app: appReducer
});