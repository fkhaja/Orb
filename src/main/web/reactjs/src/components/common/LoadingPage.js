import React from "react";
import "./LoadingPage.css";

const LoadingPage = () => {
    return (
        <div className="center-screen">
            <div className="col-sm-2 col-xs-4 text-center">
                <div className="spinner-loader"/>
            </div>
        </div>
    )
}

export default LoadingPage;