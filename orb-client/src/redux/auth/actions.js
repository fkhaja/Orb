import {login, signup} from "../../util/authUtils";
import {ACCESS_TOKEN} from "../../constants/security";
import {loadCurrentlyLoggedInUser} from "../user/actions";
import Alert from "react-s-alert";

export function logIn(loginRequest) {
    return async dispatch => {
        try {
            const response = await login(loginRequest);
            localStorage.setItem(ACCESS_TOKEN, response.accessToken);
            dispatch(loadCurrentlyLoggedInUser());
        } catch (error) {
            if (error.status === 401) Alert.error("Invalid username or password");
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
            if (error.status === 409) Alert.error("Email already exists");
        }
    }
}