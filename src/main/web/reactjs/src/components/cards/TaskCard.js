import React from 'react';
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import background from "../../img/done.png";

export default class TaskCard extends React.Component {
    render() {
        return (
            <Card style={{"max-width": '19rem'}}>
                <Card.Body>
                    <Card.Title className="text-md-center font-weight-bold">{this.props.name}</Card.Title>
                    <hr/>
                    <Card.Img variant="top" src={background}/>
                    <Card.Text className="text-muted">
                        Some quick example text to build on the card title and make up the
                        bulk of the card's content.
                    </Card.Text>
                    <Button variant="primary" block className="rounded">Open</Button>
                </Card.Body>
            </Card>
        )
    }
}