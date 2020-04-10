import React from "react";
import Navbar from "react-bootstrap/Navbar";
import {Col} from "react-bootstrap";

export default class Footer extends React.Component {
    render() {
        let fullYear = new Date().getFullYear();

        return (
            <Navbar fixed="bottom" bg="dark" variant="dark">
                <Col lg={12} className="text-center text-muted">
                    <div>
                        @ {fullYear} All Right Reserved by Sin
                    </div>
                </Col>
            </Navbar>
        )
    }
}