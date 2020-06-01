import React from 'react';
import {NavLink} from "react-router-dom";
import './Header.css'
import defaultPic from '../../img/default-avatar.png'
import {Image} from "react-bootstrap";

export default class Header extends React.Component {
    render() {
        return (
            <div className="gb-header__content">
                <h4>Workspace</h4>
                <div className="dd-profile">
                    <span className="dd-text font-weight-bold">{this.props.currentUser.username}</span>
                    <label className="dropdown">
                        <div className="dd-button">
                            <Image src={this.props.currentUser.imageUrl || defaultPic} className="avatar-pic"/>
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