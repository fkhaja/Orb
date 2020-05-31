import React from 'react';
import './NavBar.css'

export default class NavBar extends React.Component {
    render() {
        return (
            <div>
                <div className="header"/>
                <input type="checkbox" className="openSidebarMenu" id="openSidebarMenu"/>
                <label htmlFor="openSidebarMenu" className="sidebarIconToggle">
                    <div className="spinner diagonal part-1"/>
                    <div className="spinner horizontal"/>
                    <div className="spinner diagonal part-2"/>
                </label>
                <div id="sidebarMenu">
                    <ul className="sidebarMenuInner">
                        <li><a href="/link">Company</a></li>
                        <li><a href="/link">Instagram</a></li>
                        <li><a href="/link">Twitter</a></li>
                        <li><a href="/link">YouTube</a></li>
                    </ul>
                </div>
            </div>
        )
    }
}