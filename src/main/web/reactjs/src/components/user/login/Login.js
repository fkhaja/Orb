import React, {useEffect, useState} from 'react';
import './Login.css';
import {FACEBOOK_AUTH_URL, GOOGLE_AUTH_URL} from '../../../constants/Security';
import {Link, Redirect} from 'react-router-dom'
import fbLogo from '../../../img/facebook-logo.png';
import googleLogo from '../../../img/google-logo.png';
import Alert from 'react-s-alert';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Image from "react-bootstrap/Image";
import Col from "react-bootstrap/Col";
import {useDispatch} from "react-redux";
import {logIn} from "../../../redux/actions/authActions";

const Login = (props) => {

    useEffect(() => {
        if (props.location.state && props.location.state.error) {
            setTimeout(() => {
                Alert.error(props.location.state.error, {
                    timeout: 5000
                });
                props.history.replace({
                    pathname: props.location.pathname,
                    state: {}
                });
            }, 100);
        }
    }, [props.history, props.location.pathname, props.location.state]);

    return props.authenticated ? <Redirect to={{pathname: "/workspace", state: {from: props.location}}}/> :
        (
            <div className="login-container">
                <div className="login-content">
                    <h1 className="login-title font-weight-bold">LOGIN</h1>
                    <LoginForm/>
                    <br/>
                    <ProviderLogin/>
                    <br/> <br/>
                    <div className="signup-link">
                        <p>Have not account yet?</p>
                        <Link to="/signup" className="font-weight-bold">SIGN UP</Link>
                    </div>
                </div>
            </div>
        );
}

export default Login;

const ProviderLogin = () => {
    return (
        <div className="signup-link align-content-center">
            <p>Or Sign Up Using</p>
            <Container>
                <Row className="justify-content-md-center">
                    <Col xs lg="2">
                        <a href={GOOGLE_AUTH_URL}>
                            <Image src={googleLogo} roundedCircle
                                   style={{"width": "100%", "height": "auto"}}
                                   title="Google"/>
                        </a>
                    </Col>
                    <Col xs lg="2">
                        <a href={FACEBOOK_AUTH_URL}>
                            <Image src={fbLogo} roundedCircle
                                   style={{"width": "100%", "height": "auto"}}
                                   title="Facebook"/>
                        </a>
                    </Col>
                </Row>
            </Container>
        </div>
    );
}


const LoginForm = () => {
    const dispatch = useDispatch();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleEmailChange = event => setEmail(event.target.value);
    const handlePasswordChange = event => setPassword(event.target.value);
    const handleSubmit = event => {
        event.preventDefault();

        if (password.length <= 128 && email.length <= 129) {
            const loginRequest = {email: email, password: password};
            dispatch(logIn(loginRequest));
        }
    };

    return (
        <Form onSubmit={handleSubmit}>
            <Form.Group controlId="formBasicEmail">
                <Form.Control type="email" required
                              placeholder="Email"
                              name="email"
                              value={email}
                              onChange={handleEmailChange}
                              isInvalid={email.length > 129}/>
                <Form.Control.Feedback type="invalid">
                    Email length should not exceed 129 characters
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="formBasicPassword">
                <Form.Control type="password" required
                              placeholder="Password"
                              name="password"
                              value={password}
                              onChange={handlePasswordChange}
                              isInvalid={password.length > 128}/>
                <Form.Control.Feedback type="invalid">
                    Password length should not exceed 128 characters
                </Form.Control.Feedback>
            </Form.Group>
            <Button variant="primary" type="submit" block className="rounded">
                Log In
            </Button>
        </Form>
    );
}