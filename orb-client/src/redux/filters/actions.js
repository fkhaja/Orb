import {CARD_COMPLETED_FILTER, CARD_IN_PROGRESS_FILTER, CARD_NO_FILTER, CARD_TODAY_FILTER} from "./actionTypes";

export const setCardNoFilter = () => {
    return {
        type: CARD_NO_FILTER
    };
}

export const setCardInProgressFilter = () => {
    return {
        type: CARD_IN_PROGRESS_FILTER
    };
}

export const setCardCompletedFilter = () => {
    return {
        type: CARD_COMPLETED_FILTER
    };
}

export const setCardTodayFilter = () => {
    return {
        type: CARD_TODAY_FILTER
    };
}