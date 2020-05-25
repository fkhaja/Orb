import React from 'react';
import './Header.css'
import defaultPic from '../../img/default-avatar.png';
import {Image} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBars} from "@fortawesome/free-solid-svg-icons/faBars";

export default class Header extends React.Component {
    render() {
        return (
            <div className="hr-navbar">
                <ul>
                    <li>
                        <a href="#home">
                            <FontAwesomeIcon icon={faBars} color="black" size="lg"/>
                        </a>
                    </li>
                    <li>
                        <a href="#home">
                            <FontAwesomeIcon icon={faBars} color="#1cb0f6" size="lg"/>
                            <span>Workspace</span>
                        </a>
                    </li>
                    <li>
                        <a href="#about">
                            <Image src={defaultPic} className="avatar-pic"/>
                        </a>
                    </li>
                </ul>
            </div>
        );
    }
}