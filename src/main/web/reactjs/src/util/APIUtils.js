import {ACCESS_TOKEN} from '../constants/Security';
import Alert from 'react-s-alert';

export const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    });

    if (localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN));
    }

    const handleError = status => {
        if (status === 401) Alert.error("Invalid username or password");
        else if (status === 409) Alert.error("Email already exists");
        return Promise.reject();
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    if (options.method === "DELETE") return fetch(options.url, options);

    return fetch(options.url, options)
        .then(response => {
            if (response.status !== 200) {
                handleError(response.status).then(() => Promise.reject(response)).catch(console.log);
            }
            return response.json();
        });
};

