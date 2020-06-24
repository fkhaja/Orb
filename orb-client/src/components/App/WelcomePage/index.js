import React from "react";
import "./style.css";
import timeManagement from "../../../static/time-management.png";
import {useHistory} from "react-router-dom";
import Image from "react-bootstrap/Image";

const WelcomePage = () => {
    const history = useHistory();

    const handleRouteChange = () => history.push("/signup");

    return (
        <div className="landing-container">
            <div className="short-info">
                <div className="short-info-text">
                    <h1>What is Orb?</h1>
                    <span className="show-info-description">
                    Orb is a convenient tool for time planning. It will help you to be more organized in your business,
                    as well as make the most of your time
                    </span>
                    <div className="btn-registry" onClick={handleRouteChange}>
                        <span className="button pulse">
                            Registry
                        </span>
                    </div>
                </div>
                <div className="short-info-side">
                    <Image src={timeManagement} className="short-info-image"/>
                </div>
            </div>
        </div>
    );
}

export default WelcomePage;