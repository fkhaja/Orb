import React from 'react';
import {Nav, Navbar} from "react-bootstrap";

export default class NavigationBar extends React.Component{
    render() {
        return (
            <Navbar bg="dark" variant="dark">
                <Navbar.Brand href="/">Orb</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="#">Home</Nav.Link>
                    <Nav.Link href="#">Add task</Nav.Link>
                </Nav>
            </Navbar>
        );
    }
}