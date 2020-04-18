import React from "react";
import {Jumbotron} from "react-bootstrap";

export default class Welcome extends React.Component{
    render() {
        return (
            <Jumbotron className="text-dark">
                <h1>Welcome to Orb!</h1>
                <blockquote className="blockquote mb-0">
                    <p>
                        The grass is always greener on the other side.
                    </p>
                    <footer className="blockquote-footer">
                        Someone
                    </footer>
                </blockquote>
            </Jumbotron>
        )
    }
}