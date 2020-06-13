import React from 'react';
import {ACCESS_TOKEN} from '../../../constants/Security';
import {Redirect} from 'react-router-dom'

const OAuth2RedirectHandler = props => {
    const getUrlParameter = name => {
        name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
        let regex = new RegExp('[\\?&]' + name + '=([^&#]*)');

        let results = regex.exec(props.location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    };

    const token = getUrlParameter('token');
    const error = getUrlParameter('error');

    if (token) {
        localStorage.setItem(ACCESS_TOKEN, token);
        return <Redirect to={{
            pathname: "/workspace",
            state: {from: props.location}
        }}/>;
    } else {
        return <Redirect to={{
            pathname: "/login",
            state: {
                from: props.location,
                error: error
            }
        }}/>;
    }
}

export default OAuth2RedirectHandler;