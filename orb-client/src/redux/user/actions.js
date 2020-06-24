import {fetchCurrentUser} from "../../util/authUtils";
import {LOAD_LOGGED_IN_USER, LOGOUT_USER} from "./actionTypes";
import {ACCESS_TOKEN} from "../../constants/security";
import {hideLoadingPage, showLoadingPage} from "../app/actions";

export function loadCurrentlyLoggedInUser() {
    return async dispatch => {
        dispatch(showLoadingPage());
        try {
            const response = await fetchCurrentUser();
            dispatch({
                type: LOAD_LOGGED_IN_USER,
                payload: {
                    currentUser: response,
                    authenticated: true
                }
            });
        } catch (e) {
            console.log(e);
        } finally {
            dispatch(hideLoadingPage());
        }
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
    }
}