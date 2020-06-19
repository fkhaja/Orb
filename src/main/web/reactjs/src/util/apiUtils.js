import {ACCESS_TOKEN} from "../constants/security";

export const request = (options) => {
    const headers = new Headers({
        "Content-Type": "application/json",
    });

    if (localStorage.getItem(ACCESS_TOKEN)) {
        headers.append("Authorization", "Bearer " + localStorage.getItem(ACCESS_TOKEN));
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    if (options.method === "DELETE") return fetch(options.url, options);

    return fetch(options.url, options)
        .then(response => {
            if (!response.ok) {
                return Promise.reject(response);
            } else {
                return response.json();
            }
        });
};