import React from 'react';
import {Nav, Navbar} from "react-bootstrap";
import {Link, NavLink} from 'react-router-dom';

export default class NavigationBar extends React.Component {
    render() {
        return (
            <Navbar bg="dark" variant="dark">
                <Link to={"/"} className="navbar-brand">Orb</Link>
                <Nav className="mr-auto">
                    <NavLink to="/tasks" className="nav-link">Tasks</NavLink>
                    <NavLink to="/taskcards" className="nav-link">Task cards</NavLink>
                </Nav>
                {this.props.authenticated ? (
                    <Nav>
                        <NavLink to="/profile" className="nav-link">Profile</NavLink>
                        <button onClick={this.props.onLogout} className="nav-link btn btn-link">Logout</button>
                    </Nav>
                ) : (
                    <Nav>
                        <NavLink to="/login" className="nav-link">Login</NavLink>
                        <NavLink to="/signup" className="nav-link">Signup</NavLink>
                    </Nav>
                )}
            </Navbar>
        );
    }
}