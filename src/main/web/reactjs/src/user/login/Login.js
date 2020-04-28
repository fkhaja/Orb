import React from 'react';
import './Login.css';
import {ACCESS_TOKEN, FACEBOOK_AUTH_URL, GOOGLE_AUTH_URL} from '../../constants/Security';
import {login} from '../../util/APIUtils';
import {Link, Redirect} from 'react-router-dom'
import fbLogo from '../../img/facebook-logo.png';
import googleLogo from '../../img/google-logo.png';
import Alert from 'react-s-alert';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Image from "react-bootstrap/Image";
import Col from "react-bootstrap/Col";

export default class Login extends React.Component {
    componentDidMount() {
        if (this.props.location.state && this.props.location.state.error) {
            setTimeout(() => {
                Alert.error(this.props.location.state.error, {
                    timeout: 5000
                });
                this.props.history.replace({
                    pathname: this.props.location.pathname,
                    state: {}
                });
            }, 100);
        }
    }

    render() {
        if (this.props.authenticated) {
            return <Redirect
                to={{
                    pathname: "/workspace",
                    state: {from: this.props.location}
                }}/>;
        }

        return (
            <div className="login-container">
                <div className="login-content">
                    <h1 className="login-title font-weight-bold">LOGIN</h1>
                    <LoginForm {...this.props} />
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
}

class ProviderLogin extends React.Component {
    render() {
        return (
            <div style={{"text-align": "center"}} className="signup-link">
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
}


class LoginForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
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

        const loginRequest = Object.assign({}, this.state);

        login(loginRequest)
            .then(response => {
                localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                Alert.success("You're successfully logged in!");
                this.props.history.push("/");
            }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        });
    }

    render() {
        return (
            <Form onSubmit={this.handleSubmit}>
                <Form.Group controlId="formBasicEmail">
                    <Form.Control type="email" placeholder="Email" name="email" value={this.state.email}
                                  onChange={this.handleInputChange} required/>
                </Form.Group>
                <Form.Group controlId="formBasicPassword">
                    <Form.Control type="password" placeholder="Password" name="password" value={this.state.password}
                                  onChange={this.handleInputChange} required/>
                </Form.Group>
                <Button variant="primary" type="submit" block className="rounded">
                    Log In
                </Button>
            </Form>
        );
    }
}