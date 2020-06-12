import {HIDE_LOADING_PAGE, SHOW_LOADING_PAGE} from "../types";

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