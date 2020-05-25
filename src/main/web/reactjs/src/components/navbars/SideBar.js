import React from 'react';
import './SideBar.css'
import {Link} from "react-router-dom";

export default class SideBar extends React.Component {
    render() {
        return (
            <aside className="sidebar">
                <div id="leftside-navigation" className="nano">
                    <ul className="nano-content">
                        <li>
                            <Link to={"/workspace"}>
                                <i className="fa fa-dashboard"/>
                                <span>Workspace</span>
                            </Link>
                        </li>
                        <li className="sub-menu">
                            <Link to={"/"}>
                                <i className="fa fa-cogs"/>
                                <span>Tables</span>
                                <i className="arrow fa fa-angle-right pull-right"/>
                            </Link>
                            <ul/>
                        </li>
                        <li className="sub-menu">
                            <Link to={"/"}>
                                <i className="fa fa-cogs"/>
                                <span>Labels</span>
                                <i className="arrow fa fa-angle-right pull-right"/>
                            </Link>
                            <ul/>
                        </li>
                    </ul>
                </div>
            </aside>
        )
    }
}