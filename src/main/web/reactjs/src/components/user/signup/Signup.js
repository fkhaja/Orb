import React, {Component} from 'react';
import './Signup.css';
import {Link, Redirect} from 'react-router-dom'
import {FACEBOOK_AUTH_URL, GOOGLE_AUTH_URL} from '../../../constants/Security';
import fbLogo from '../../../img/facebook-logo.png';
import googleLogo from '../../../img/google-logo.png';
import Alert from 'react-s-alert';
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Image from "react-bootstrap/Image";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {signUp} from "../../../util/AuthUtils";

class Signup extends Component {
    render() {
        if (this.props.authenticated) {
            return <Redirect
                to={{
                    pathname: "/",
                    state: {from: this.props.location}
                }}/>;
        }

        return (
            <div className="signup-container">
                <div className="signup-content">
                    <h1 className="signup-title font-weight-bold">SIGN UP</h1>
                    <SignUpForm {...this.props} />
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
}


class ProviderSignUp extends Component {
    render() {
        return (
            <div style={{"text-align": "center"}} className="signup-link">
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
}

class SignUpForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            email: '',
            password: ''
        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName]: inputValue
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        const signUpRequest = Object.assign({}, this.state);

        signUp(signUpRequest)
            .then(response => {
                Alert.success("You're successfully registered. Please login to continue!");
                this.props.history.push("/login");
            }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        });
    }

    render() {
        return (
            <Form onSubmit={this.handleSubmit}>
                <Form.Group controlId="formBasicUsername">
                    <Form.Control type="text" placeholder="Username" name="username" value={this.state.username}
                                  onChange={this.handleInputChange} required/>
                </Form.Group>
                <Form.Group controlId="formBasicEmail">
                    <Form.Control type="email" placeholder="Email" name="email" value={this.state.email}
                                  onChange={this.handleInputChange} required/>
                </Form.Group>
                <Form.Group controlId="formBasicPassword">
                    <Form.Control type="password" placeholder="Password" name="password" value={this.state.password}
                                  onChange={this.handleInputChange} required/>
                </Form.Group>
                <Button variant="primary" type="submit" block className="rounded ">
                    Sign Up
                </Button>
            </Form>

        );
    }
}

export default Signup