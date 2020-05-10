import React from 'react';
import {Redirect, Route} from "react-router-dom";
import SideBar from "../navbars/SideBar";


const PrivateRoute = ({component: Component, authenticated, ...rest}) => (
    <Route
        {...rest}
        render={props =>
            authenticated ?
                (
                    <div className="grid-container">
                        <SideBar className="grid-col-1"/>
                        <div className="grid-col-2">
                            <Component {...rest} {...props}/>
                        </div>
                    </div>
                ) : (
                    <Redirect
                        to={{
                            pathname: '/login',
                            state: {from: props.location}
                        }}
                    />
                )
        }
    />
);

export default PrivateRoute;