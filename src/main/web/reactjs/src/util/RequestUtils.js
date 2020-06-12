import {API_BASE_URL} from "../constants/Security";
import {request} from "./APIUtils";

export async function findTaskCards(page = 0, size = 28) {
    return request({
        url: `${API_BASE_URL}/taskcards?page=${page}&size=${size}`,
        method: 'GET'
    })
}

export async function updateTask(task, taskId, cardId) {
    return request({
        url: `${API_BASE_URL}/taskcards/${cardId}/tasks/${taskId}`,
        method: 'PUT',
        body: JSON.stringify(task)
    })
}

export async function saveTask(task, cardId) {
    return request({
        url: `${API_BASE_URL}/taskcards/${cardId}/tasks`,
        method: 'POST',
        body: JSON.stringify(task)
    })
}

export async function removeTask(taskId, cardId) {
    return request({
        url: `${API_BASE_URL}/taskcards/${cardId}/tasks/${taskId}`,
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

export async function updateTaskCard(id, replacement) {
    return request({
        url: `${API_BASE_URL}/taskcards/${id}`,
        method: 'PUT',
        body: JSON.stringify(replacement)
    })
}

export async function deleteTaskCard(id) {
    return request({
        url: `${API_BASE_URL}/taskcards/${id}`,
        method: 'DELETE'
    })
}
