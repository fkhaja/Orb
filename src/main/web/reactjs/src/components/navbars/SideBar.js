import React from 'react';
import './SideBar.css'
import houseIcon from '../../img/house.png';
import cardIcon from '../../img/card.png';
import {Image} from "react-bootstrap";
import {Link} from "react-router-dom";

export default class SideBar extends React.Component {
    render() {
        return (
            <div className="sidenav">
                <Link to={"/workspace"} className="gb-left-menu__logo colorize scale">
                    <Image className="sidenav-icon-main" src={houseIcon}/>
                </Link>
                <Link to={"/workspace"} className="gb-left-menu__nav-item"
                      style={{"text-decoration": "none", "color": "#50667b"}}>
                    <Image className="sidenav-icon" src={cardIcon}/>
                    Workspace
                </Link>
            </div>
        )
    }
}