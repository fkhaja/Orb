import React from 'react';
import {NavLink} from "react-router-dom";
import './Header.css'

export default class Header extends React.Component {
    render() {
        return (
            <div className="gb-header__content">
                <h3>My task cards</h3>
                <div>
                    <span className="dd-text font-weight-bold">{this.props.currentUser.username}</span>
                    <label className="dropdown">
                        <div className="dd-button">
                            <img src={this.props.currentUser.imageUrl} className="avatar-pic"/>
                        </div>
                        <input type="checkbox" className="dd-input" id="test"/>
                        <ul className="dd-menu">
                            <li>
                                <NavLink to="/profile" style={{"text-decoration": "none"}} className="text-dark">
                                    Profile
                                </NavLink>
                            </li>
                            <li className="divider"/>
                            <li>
                                <button className="text-button" onClick={this.props.onLogout}>Logout</button>
                            </li>
                        </ul>
                    </label>
                </div>
            </div>
        )
    }
}