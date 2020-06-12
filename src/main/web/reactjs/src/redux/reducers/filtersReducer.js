import {CARD_COMPLETED_FILTER, CARD_IN_PROGRESS_FILTER, CARD_NO_FILTER, CARD_TODAY_FILTER} from "../types";
import moment from "moment";

const initialState = {
    cardFilter: card => card
};

export const filtersReducer = (state = initialState, action) => {
    switch (action.type) {
        case CARD_NO_FILTER:
            return {...state, cardFilter: card => card};
        case CARD_IN_PROGRESS_FILTER:
            return {...state, cardFilter: card => !card.done};
        case CARD_COMPLETED_FILTER:
            return {...state, cardFilter: card => card.done};
        case CARD_TODAY_FILTER:
            return {...state, cardFilter: card => moment(card.creationDate).isSame(Date.now(), "day")};
        default:
            return state;
    }
};