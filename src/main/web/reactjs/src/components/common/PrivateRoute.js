import React from 'react';
import {Redirect, Route} from "react-router-dom";
import Header from "../navbars/Header";


const PrivateRoute = ({component: Component, authenticated, ...rest}) => (
    <Route
        {...rest}
        render={props =>
            authenticated ?
                (
                    <div>
                        <Header/>
                        <div className="workspace-box">
                            <div className="workspace">
                                <Component {...rest} {...props}/>
                            </div>
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