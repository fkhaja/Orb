import {API_BASE_URL} from "../constants/security";
import {request} from "./apiUtils";

export async function findTaskCards(page = 0, size = 28) {
    return request({
        url: `${API_BASE_URL}/taskcards?page=${page}&size=${size}`,
        method: "GET"
    })
}

export async function partlyUpdateTask(update, taskId, cardId) {
    return request({
        url: `${API_BASE_URL}/taskcards/${cardId}/tasks/${taskId}`,
        method: "PATCH",
        body: JSON.stringify(update)
    })
}

export async function saveTask(task, cardId) {
    return request({
        url: `${API_BASE_URL}/taskcards/${cardId}/tasks`,
        method: "POST",
        body: JSON.stringify(task)
    })
}

export async function removeTask(taskId, cardId) {
    return request({
        url: `${API_BASE_URL}/taskcards/${cardId}/tasks/${taskId}`,
        method: "DELETE"
    })
}

export async function saveTaskCard(card) {
    return request({
        url: `${API_BASE_URL}/taskcards`,
        method: "POST",
        body: JSON.stringify(card)
    })
}

export async function partlyUpdateTaskCard(id, update) {
    return request({
        url: `${API_BASE_URL}/taskcards/${id}`,
        method: "PATCH",
        body: JSON.stringify(update)
    })
}

export async function deleteTaskCard(id) {
    return request({
        url: `${API_BASE_URL}/taskcards/${id}`,
        method: "DELETE"
    })
}