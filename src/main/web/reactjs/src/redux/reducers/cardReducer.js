import {
    CANCEL_TASK_COMPLETION,
    COMPLETE_TASK,
    CREATE_CARD,
    CREATE_TASK,
    DELETE_CARD,
    DELETE_TASK,
    EDIT_CARD_DESCRIPTION,
    EDIT_CARD_TERM,
    EDIT_TASK,
    FETCH_CARDS
} from "../types";

const initialState = {
    fetchedCards: []
};

export const cardReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_CARDS:
            return {...state, fetchedCards: action.payload};
        case CREATE_CARD:
            return {...state, fetchedCards: state.fetchedCards.concat([action.payload])};
        case DELETE_CARD:
            return {...state, fetchedCards: state.fetchedCards.filter(card => card.cardId !== action.id)};
        case EDIT_CARD_DESCRIPTION:
            return {
                ...state,
                fetchedCards: state.fetchedCards.map(card => card.cardId === action.id ?
                    {...card, description: action.description} : card
                )
            };
        case EDIT_CARD_TERM:
            return {
                ...state,
                fetchedCards: state.fetchedCards.map(card => card.cardId === action.id ?
                    {...card, term: action.term} : card
                )
            };
        case CREATE_TASK:
            return {
                ...state, fetchedCards: state.fetchedCards.map(card => {
                    if (card.cardId === action.cardId) {
                        card.tasks.push(action.payload);
                    }
                    return card;
                })
            };
        case EDIT_TASK:
            return {
                ...state, fetchedCards: state.fetchedCards.map(card => {
                    if (card.cardId === action.cardId) {
                        card.tasks.map(task => {
                            if(task.taskId === action.taskId){
                                task.value = action.value;
                            }
                            return task;
                        })
                    }
                    return card;
                })
            };
        case DELETE_TASK:
            return {
                ...state, fetchedCards: state.fetchedCards.map(card => {
                    if (card.cardId === action.cardId) {
                        card.tasks = card.tasks.filter(task => task.taskId !== action.taskId)
                    }
                    return card;
                })
            };
        case COMPLETE_TASK:
            return {
                ...state, fetchedCards: state.fetchedCards.map(card => {
                    if (card.cardId === action.cardId) {
                        card.tasks.map(task => {
                            if(task.taskId === action.taskId){
                                task.completed = true;
                            }
                            return task;
                        })
                    }
                    return card;
                })
            };
        case CANCEL_TASK_COMPLETION:
            return {
                ...state, fetchedCards: state.fetchedCards.map(card => {
                    if (card.cardId === action.cardId) {
                        card.tasks.map(task => {
                            if(task.taskId === action.taskId){
                                task.completed = false;
                            }
                            return task;
                        })
                    }
                    return card;
                })
            };
        default:
            return state;
    }
};