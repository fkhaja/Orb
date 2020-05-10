import React from 'react';
import {NavLink} from "react-router-dom";
import './Header.css'
import defaultPic from '../../img/default-avatar.png';
import {Image} from "react-bootstrap";

export default class Header extends React.Component {
    render() {
        return (
            <div>
                <div className="gb-header__content">
                    <h3>{this.props.title}</h3>
                    <div>
                        <span className="dd-text font-weight-bold">{this.props.currentUser.username}</span>
                        <label className="dropdown">
                            <div className="dd-button">
                                <Image
                                    src={this.props.currentUser.imageUrl || defaultPic} className="avatar-pic"/>
                            </div>
                            <input type="checkbox" className="dd-input" id="test"/>
                            <ul className="dd-menu">
                                <li>
                                    <NavLink to="/profile" style={{"textDecoration": "none"}} className="text-dark">
                                        Profile
                                    </NavLink>
                                </li>
                                <li className="divider"/>
                                <li onClick={this.props.onLogout}>Logout</li>
                            </ul>
                        </label>
                    </div>
                </div>
                <br/>
            </div>
        )
    }
}