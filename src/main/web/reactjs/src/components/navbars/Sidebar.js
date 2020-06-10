import React from 'react';
import './Sidebar.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFilter, faListAlt, faTag} from "@fortawesome/free-solid-svg-icons";

const Sidebar = () => {
    return (
        <div>
            <div className="sidebar">
                <div className="sidebar-header">
                    <h1>Orb</h1>
                </div>
                <div className="divider"/>
                <ul className="sidebar-menu">
                    <li className="sidebar-item">
                        <FontAwesomeIcon icon={faListAlt}/>
                        <span>Workspace</span>
                    </li>
                    <li className="sidebar-item" style={{"marginTop": "25px"}}>
                        <div className="tabs">
                            <div className="tab">
                                <input type="checkbox" id="chck2" className="accordion_input"/>
                                <label className="tab-label" htmlFor="chck2">
                                    <div>
                                        <FontAwesomeIcon icon={faTag}/>
                                        <span>Categories</span>
                                    </div>
                                </label>
                                <div className="tab-content">
                                    <ul className="submenu">
                                        <li>
                                            <span>All</span>
                                        </li>
                                        <li>
                                            <span>In process</span>
                                        </li>
                                        <li>
                                            <span>Completed</span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li className="sidebar-item">
                        <div className="tabs">
                            <div className="tab">
                                <input type="checkbox" id="chck1" className="accordion_input"/>
                                <label className="tab-label" htmlFor="chck1">
                                    <div>
                                        <FontAwesomeIcon icon={faFilter}/>
                                        <span>Filters</span>
                                    </div>
                                </label>
                                <div className="tab-content">
                                    <ul className="submenu">
                                        <li>
                                            <span>Filter 1</span>
                                        </li>
                                        <li>
                                            <span>Filter 2</span>
                                        </li>
                                        <li>
                                            <span>Filter 3</span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    )
}

export default Sidebar;