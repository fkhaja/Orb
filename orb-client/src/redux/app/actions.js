import {HIDE_LOADER, HIDE_LOADING_PAGE, SHOW_LOADER, SHOW_LOADING_PAGE} from "./actionTypes";

export function showLoadingPage() {
    return {
        type: SHOW_LOADING_PAGE
    };
}

export function hideLoadingPage() {
    return {
        type: HIDE_LOADING_PAGE
    };
}

export function showLoader() {
    return {
        type: SHOW_LOADER
    };
}

export function hideLoader() {
    return {
        type: HIDE_LOADER
    };
}