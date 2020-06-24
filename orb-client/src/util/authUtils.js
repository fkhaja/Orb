import {ACCESS_TOKEN, API_BASE_URL} from "../constants/security";
import {request} from "./apiUtils";

export async function fetchCurrentUser() {
    if (localStorage.getItem(ACCESS_TOKEN)) {
        return request({
            url: API_BASE_URL + "/user/me",
            method: "GET"
        });
    }
    return Promise.reject("No access token set.");
}

export async function login(loginRequest) {
    return request({
        url: API_BASE_URL + "/auth/login",
        method: "POST",
        body: JSON.stringify(loginRequest)
    });
}

export async function signup(signUpRequest) {
    return request({
        url: API_BASE_URL + "/auth/signup",
        method: "POST",
        body: JSON.stringify(signUpRequest)
    });
}