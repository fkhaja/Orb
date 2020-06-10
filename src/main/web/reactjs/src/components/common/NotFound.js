import React from 'react';
import Jumbotron from "react-bootstrap/Jumbotron";

const NotFound = () => {
    return (
        <Jumbotron>
            <h1 className="text-center">404 Error</h1>
            <p className="text-center">
                The Page you're looking for was not found.
            </p>
        </Jumbotron>
    );
}

export default NotFound;