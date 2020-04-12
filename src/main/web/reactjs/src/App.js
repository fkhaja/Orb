import React from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import {Col, Container, Row} from "react-bootstrap";

import NavigationBar from "./components/NavigationBar";
import Welcome from "./components/Welcome";
import Footer from "./components/Footer";
import TaskList from "./components/TaskList";
import TaskCardList from "./components/TaskCardList";

function App() {
    const marginTop = {
        marginTop: "20px"
    };

    return (
        <Router>
            <NavigationBar/>
            <Container>
                <Row>
                    <Col style={marginTop}>
                        <Switch>
                            <Route path="/home" exact component={Welcome}/>
                            <Route path="/tasks" exact component={TaskList}/>
                            <Route path="/taskCards" exact component={TaskCardList}/>
                        </Switch>
                    </Col>
                </Row>
            </Container>
            <Footer/>
        </Router>
    )
}

export default App;