import React from 'react';
import './Sidebar.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFilter, faListAlt} from "@fortawesome/free-solid-svg-icons";
import {useDispatch} from "react-redux";
import {
    setCardCompletedFilter,
    setCardInProgressFilter,
    setCardNoFilter,
    setCardTodayFilter
} from "../../redux/actions/filterActions";

const Sidebar = () => {
    const dispatch = useDispatch();

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
                                <input type="checkbox" id="chck1" className="accordion_input"/>
                                <label className="tab-label" htmlFor="chck1">
                                    <div>
                                        <FontAwesomeIcon icon={faFilter}/>
                                        <span>Filters</span>
                                    </div>
                                </label>
                                <div className="tab-content">
                                    <ul className="submenu">
                                        <li tabIndex={1} onClick={() => dispatch(setCardNoFilter())}>
                                            <span>No filter</span>
                                        </li>
                                        <li tabIndex={2} onClick={() => dispatch(setCardInProgressFilter())}>
                                            <span>In progress</span>
                                        </li>
                                        <li tabIndex={3} onClick={() => dispatch(setCardCompletedFilter())}>
                                            <span>Completed</span>
                                        </li>
                                        <li tabIndex={4} onClick={() => dispatch(setCardTodayFilter())}>
                                            <span>Today</span>
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