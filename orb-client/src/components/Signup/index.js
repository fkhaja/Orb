import React, {useState} from "react";
import "./style.css";
import {Link, Redirect} from "react-router-dom"
import {FACEBOOK_AUTH_URL, GOOGLE_AUTH_URL} from "../../constants/security";
import fbLogo from "../../static/social/facebook-logo.png";
import googleLogo from "../../static/social/google-logo.png";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Image from "react-bootstrap/Image";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch} from "react-redux";
import {signUp} from "../../redux/auth/actions";

const Signup = props => {
    return props.authenticated ? <Redirect to={{pathname: "/", state: {from: props.location}}}/> :
        (
            <div className="signup-container">
                <div className="signup-content">
                    <h1 className="signup-title font-weight-bold">SIGN UP</h1>
                    <SignUpForm {...props} />
                    <br/>
                    <ProviderSignUp/>
                    <br/> <br/>
                    <div className="login-link">
                        <p>Already have an account?</p>
                        <Link to="/login" className="font-weight-bold">LOGIN</Link>
                    </div>
                </div>
            </div>
        );
}


const ProviderSignUp = () => {
    return (
        <div className="signup-link align-content-center">
            <p>Or Login With</p>
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

const SignUpForm = () => {
    const dispatch = useDispatch();
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleUsernameChange = event => setUsername(event.target.value);
    const handleEmailChange = event => setEmail(event.target.value);
    const handlePasswordChange = event => setPassword(event.target.value);
    const handleSubmit = event => {
        event.preventDefault();
        const isValid = (username.length >= 4 && username.length <= 16) && email.length <= 129 && password.length <= 128;
        if (isValid) {
            const signUpRequest = {username: username, email: email, password: password};
            dispatch(signUp(signUpRequest));
        }
    }

    return (
        <Form onSubmit={handleSubmit}>
            <Form.Group controlId="formBasicUsername">
                <Form.Control type="text" required
                              placeholder="Username"
                              name="username"
                              value={username}
                              onChange={handleUsernameChange}
                              isInvalid={(username.length > 0 && username.length < 4) || username.length > 16}/>
                <Form.Control.Feedback type="invalid">
                    Username must be between 4 and 16 characters
                </Form.Control.Feedback>
            </Form.Group>
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
                Sign Up
            </Button>
        </Form>

    );
}

export default Signup;