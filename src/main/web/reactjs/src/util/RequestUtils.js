import {API_BASE_URL} from "../constants/Security";
import {request} from "./APIUtils";

export async function findTaskCards() {
    return request({
        url: `${API_BASE_URL}/taskcards`,
        method: 'GET'
    })
}

export async function updateTask(task, cardId) {
    return request({
        url: `${API_BASE_URL}/taskcards/${cardId}/tasks/${task.taskId}`,
        method: 'PUT',
        body: JSON.stringify(task)
    })
}

export async function findAllTasksForCard(cardId) {
    return request({
        url: `${API_BASE_URL}/taskcards/${cardId}/tasks`,
        method: 'GET'
    })
}

export async function saveTask(task, cardId) {
    return request({
        url: `${API_BASE_URL}/taskcards/${cardId}/tasks`,
        method: 'POST',
        body: JSON.stringify(task)
    })
}

export async function deleteTask(task, cardId) {
    return request({
        url: `${API_BASE_URL}/taskcards/${cardId}/tasks/${task.taskId}`,
        method: 'DELETE'
    })
}

export async function saveTaskCard(card) {
    return request({
        url: `${API_BASE_URL}/taskcards`,
        method: 'POST',
        body: JSON.stringify(card)
    })
}

export async function updateTaskCard(card) {
    console.log(card);
    return request({
        url: `${API_BASE_URL}/taskcards/${card.cardId}`,
        method: 'PUT',
        body: JSON.stringify(card)
    })
}

export async function deleteTaskCard(card) {
    return request({
        url: `${API_BASE_URL}/taskcards/${card.cardId}`,
        method: 'DELETE'
    })
}
