import React from 'react';
import {Nav, Navbar} from "react-bootstrap";
import {Link} from 'react-router-dom';

export default class NavigationBar extends React.Component{
    render() {
        return (
            <Navbar bg="dark" variant="dark">
                <Link to={"home"} className="navbar-brand">Orb</Link>
                <Nav className="mr-auto">
                    <Link to={"home"} className="nav-link">Home</Link>
                    <Link to={"tasks"} className="nav-link">Tasks</Link>
                </Nav>
            </Navbar>
        );
    }
}