import React, {useEffect} from "react";
import {Redirect, Route, Switch} from "react-router-dom";
import Login from "../Login";
import Signup from "../Signup";
import OAuth2RedirectHandler from "../OAuth2";
import PrivateRoute from "./PrivateRoute";
import Alert from "react-s-alert";
import "react-s-alert/dist/s-alert-default.css";
import "react-s-alert/dist/s-alert-css-effects/slide.css";
import NotFound from "./NotFound";
import Workspace from "../Workspace";
import {useDispatch, useSelector} from "react-redux";
import {loadCurrentlyLoggedInUser} from "../../redux/user/actions";
import LoadingPage from "./LoadingPage";
import WelcomePage from "./WelcomePage";

const App = () => {
    const dispatch = useDispatch();
    const user = useSelector(state => state.user);
    const showLoadingPage = useSelector(state => state.app.showLoadingPage);

    useEffect(() => {
        dispatch(loadCurrentlyLoggedInUser());
    }, [dispatch]);

    return (
        <div>
            <div>
                {showLoadingPage ? <LoadingPage/> :
                    <Switch>
                        <Route exact path="/">
                            {user.currentUser ? <Redirect to="/workspace"/> : <Route path="/" component={WelcomePage}/>}
                        </Route>
                        <PrivateRoute exact path="/workspace" authenticated={user.authenticated} component={Workspace}/>
                        <Route path="/login"
                               render={(props) => <Login authenticated={user.authenticated} {...props} />}/>
                        <Route path="/signup"
                               render={(props) => <Signup authenticated={user.authenticated} {...props} />}/>
                        <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}/>
                        <Route component={NotFound}/>
                    </Switch>
                }
            </div>
            <Alert stack={{limit: 3}}
                   timeout={3000}
                   position="top-right" effect="slide" offset={65}/>
        </div>
    );
}

export default App;