import {fetchCurrentUser} from "../../util/AuthUtils";
import {LOAD_LOGGED_IN_USER, LOGOUT_USER} from "../types";
import {ACCESS_TOKEN} from "../../constants/Security";
import Alert from "react-s-alert";

export function loadCurrentlyLoggedInUser() {
    return async dispatch => {
        const response = await fetchCurrentUser();
        dispatch({
            type: LOAD_LOGGED_IN_USER,
            payload: {
                currentUser: response,
                authenticated: true
            }
        });
    };
}

export function logoutUser() {
    localStorage.removeItem(ACCESS_TOKEN);
    return dispatch => {
        dispatch({
            type: LOGOUT_USER,
            payload: {
                currentUser: null,
                authenticated: false
            }
        })
        Alert.success("You're safely logged out!");
    }
}