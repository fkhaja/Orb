import React from 'react';
import './Header.css'
import defaultPic from '../../img/default-avatar.png'
import {Image} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import {logoutUser} from "../../redux/actions/userActions";

const Header = () => {
    const dispatch = useDispatch();
    const currentUser = useSelector(state => state.user.currentUser);

    return (
        <div className="gb-header__content">
            <h4>Workspace</h4>
            <div className="dd-profile">
                <span className="dd-text font-weight-bold">{currentUser.username}</span>
                <label className="dropdown">
                    <div className="dd-button">
                        <Image src={currentUser.imageUrl || defaultPic} className="avatar-pic" />
                    </div>
                    <input type="checkbox" className="dd-input" id="test"/>
                    <ul className="dd-menu">
                        <li onClick={() => dispatch(logoutUser())}>Logout</li>
                    </ul>
                </label>
            </div>
        </div>
    )
}

export default Header;