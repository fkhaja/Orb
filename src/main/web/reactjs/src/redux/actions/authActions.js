import {login, signup} from "../../util/AuthUtils";
import {ACCESS_TOKEN} from "../../constants/Security";
import {loadCurrentlyLoggedInUser} from "./userActions";

export function logIn(loginRequest) {
    return async dispatch => {
        try {
            const response = await login(loginRequest);
            localStorage.setItem(ACCESS_TOKEN, response.accessToken);
            dispatch(loadCurrentlyLoggedInUser());
        } catch (error) {
            console.log(error);
        }
    }
}

export function signUp(signUpRequest) {
    return async dispatch => {
        try {
            await signup(signUpRequest);
            const loginRequest = {email: signUpRequest.email, password: signUpRequest.password};
            dispatch(logIn(loginRequest))
        } catch (error) {
            console.log(error);
        }
    }
}