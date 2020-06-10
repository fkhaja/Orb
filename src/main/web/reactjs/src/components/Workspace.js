import React from 'react';
import TaskCardList from "./cards/TaskCardList";
import './Workspace.css';
import Sidebar from "./navbars/Sidebar";
import Header from "./navbars/Header";

const Workspace = () => {
    return (
        <div className="workspace-box">
            <Sidebar className="sidebar"/>
            <div className="workspace">
                <Header/>
                <TaskCardList/>
            </div>
        </div>
    );
};

export default Workspace;