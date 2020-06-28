import React from "react";
import TaskCardList from "../TaskCardList";
import "./style.css";
import Sidebar from "./SideBar";
import Header from "./Header";

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