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