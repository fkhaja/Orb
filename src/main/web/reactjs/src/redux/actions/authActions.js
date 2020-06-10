import {login, signup} from "../../util/AuthUtils";
import {ACCESS_TOKEN} from "../../constants/Security";
import Alert from "react-s-alert";
import {loadCurrentlyLoggedInUser} from "./userActions";

export function logIn(loginRequest) {
    return async dispatch => {
        try {
            const response = await login(loginRequest);
            localStorage.setItem(ACCESS_TOKEN, response.accessToken);
            dispatch(loadCurrentlyLoggedInUser());
        } catch (error) {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        }
    }
}

export function signUp(signUpRequest) {
    return async dispatch => {
        try {
            await signup(signUpRequest);
            const loginRequest = {email: signUpRequest.email, password: signUpRequest.password};
            dispatch(logIn(loginRequest))
            Alert.success("You're successfully registered!");
        } catch (error) {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        }
    }
}