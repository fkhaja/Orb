import React, {useEffect} from 'react';
import {Redirect, Route, Switch} from 'react-router-dom';
import Login from './user/login/Login';
import Signup from './user/signup/Signup';
import Profile from './user/profile/Profile';
import OAuth2RedirectHandler from './user/oauth2/OAuth2RedirectHandler';
import PrivateRoute from './common/PrivateRoute';
import Alert from 'react-s-alert';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import NotFound from "./common/NotFound";
import Workspace from "./Workspace";
import {useDispatch, useSelector} from "react-redux";
import {loadCurrentlyLoggedInUser} from "../redux/actions/userActions";

const App = () => {
    const dispatch = useDispatch();
    const user = useSelector(state => state.user);

    useEffect(() => {
        dispatch(loadCurrentlyLoggedInUser());
    }, [dispatch]);

    return (
        <div>
            <div>
                <Switch>
                    <Route exact path="/">
                        <Redirect to="/workspace"/>
                    </Route>
                    <PrivateRoute exact path="/workspace" authenticated={user.authenticated} component={Workspace}/>
                    <PrivateRoute path="/profile" authenticated={user.authenticated}
                                  currentUser={user.currentUser}
                                  component={Profile}/>
                    <Route path="/login"
                           render={(props) => <Login authenticated={user.authenticated} {...props} />}/>
                    <Route path="/signup"
                           render={(props) => <Signup authenticated={user.authenticated} {...props} />}/>
                    <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}/>
                    <Route component={NotFound}/>
                </Switch>
            </div>
            <Alert stack={{limit: 3}}
                   timeout={3000}
                   position='top-right' effect='slide' offset={65}/>
        </div>
    );
}

export default App;