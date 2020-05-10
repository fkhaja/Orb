import React, {Component} from 'react';
import {Redirect, Route, Switch} from 'react-router-dom';
import Login from './user/login/Login';
import Signup from './user/signup/Signup';
import Profile from './user/profile/Profile';
import OAuth2RedirectHandler from './user/oauth2/OAuth2RedirectHandler';
import {getCurrentUser} from '../util/AuthUtils';
import {ACCESS_TOKEN} from '../constants/Security';
import PrivateRoute from './common/PrivateRoute';
import Alert from 'react-s-alert';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import NotFound from "./common/NotFound";
import Workspace from "./Workspace";
import TaskCardContent from "./cards/TaskCardContent";

export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            authenticated: false,
            currentUser: null,
        };

        this.loadCurrentlyLoggedInUser = this.loadCurrentlyLoggedInUser.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
        this.handleLogin = this.handleLogin.bind(this);
    }

    loadCurrentlyLoggedInUser() {
        getCurrentUser()
            .then(response => {
                this.setState({
                    currentUser: response,
                    authenticated: true,
                });
            }).catch(e => console.log(e));
    }

    handleLogout() {
        localStorage.removeItem(ACCESS_TOKEN);
        this.setState({
            authenticated: false,
            currentUser: null
        });
        Alert.success("You're safely logged out!");
    }

    handleLogin() {
        this.loadCurrentlyLoggedInUser();
    }

    componentDidMount() {
        this.loadCurrentlyLoggedInUser();
    }

    render() {
        return (
            <div>
                <div>
                    <Switch>
                        <Route exact path="/">
                            <Redirect to="/workspace"/>
                        </Route>
                        <PrivateRoute exact path={"/workspace"} authenticated={this.state.authenticated}
                                      currentUser={this.state.currentUser}
                                      component={Workspace}
                                      onLogout={this.handleLogout}/>
                        <PrivateRoute exact path={"/workspace/taskcards/:id"} component={TaskCardContent}
                                      authenticated={this.state.authenticated}
                                      currentUser={this.state.currentUser}
                                      onLogout={this.handleLogout}/>
                        <PrivateRoute path="/profile" authenticated={this.state.authenticated}
                                      currentUser={this.state.currentUser}
                                      component={Profile}
                                      onLogout={this.handleLogout}/>
                        <Route path="/login"
                               render={(props) => <Login authenticated={this.state.authenticated}
                                                         currentUser={this.state.currentUser}
                                                         onLogin={this.handleLogin} {...props} />}/>
                        <Route path="/signup"
                               render={(props) => <Signup authenticated={this.state.authenticated} {...props} />}/>
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
}