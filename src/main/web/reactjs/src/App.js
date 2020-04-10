import React from 'react';
import NavigationBar from "./components/NavigationBar";
import {Col, Container, Row} from "react-bootstrap";
import Welcome from "./components/Welcome";
import Footer from "./components/Footer";

function App() {
    const marginTop = {
        marginTop: "20px"
    };

    return (
        <div className="App">
            <NavigationBar/>
            <Container>
                <Row>
                    <Col style={marginTop}>
                        <Welcome/>
                    </Col>
                </Row>
            </Container>
            <Footer/>
        </div>
    )
}

export default App;