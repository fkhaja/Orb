import React from 'react';
import {Dropdown, DropdownButton, Nav, Navbar} from "react-bootstrap";
import {Link, NavLink} from 'react-router-dom';

export default class NavigationBar extends React.Component {
    render() {
        return (
            <Navbar bg="white">
                <Link to={"/workspace"} className="navbar-brand text-dark font-weight-bold mr-auto">Orb</Link>
                {this.props.authenticated ? (
                    <Nav>
                        <DropdownButton alignRight id="dropdown-menu-align-right" variant="transparent">
                            <Dropdown.Item>
                                <NavLink to="/profile" style={{"text-decoration": "none"}}
                                         className="text-dark">Profile</NavLink>
                            </Dropdown.Item>
                            <Dropdown.Item>
                                <NavLink to="/taskcards" style={{"text-decoration": "none"}} className="text-dark">My
                                    cards</NavLink>
                            </Dropdown.Item>
                            <Dropdown.Divider/>
                            <Dropdown.Item onClick={this.props.onLogout}>Logout</Dropdown.Item>
                        </DropdownButton>
                    </Nav>
                ) : (<Nav/>)}
            </Navbar>
        );
    }
}