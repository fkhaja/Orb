import React from 'react';
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";


export default class TaskCard extends React.Component {
    render() {
        return (
            <Card style={{width: '25rem'}}>
                <Card.Img variant="top"/>
                <Card.Body>
                    <Card.Title className="text-md-center">{this.props.name}</Card.Title>
                    <Card.Text className="text-muted">
                        Some quick example text to build on the card title and make up the
                        bulk of the card's content.
                    </Card.Text>
                    <Button variant="primary">Go somewhere</Button>
                </Card.Body>
            </Card>
        )
    }
}