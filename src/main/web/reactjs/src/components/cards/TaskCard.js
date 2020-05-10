import React from 'react';
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import background from "../../img/done.png";
import './TaskCard.css'
import {Link} from "react-router-dom";

export default class TaskCard extends React.Component {

    render() {
        return (
            <Card style={{"maxWidth": '19rem'}} className="scale">
                <Card.Body>
                    <Card.Title className="text-md-center font-weight-bold">{this.props.card.name}</Card.Title>
                    <hr/>
                    <Card.Img variant="top" src={background}/>
                    <Card.Text className="text-muted">
                        Some quick example text to build on the card title and make up the
                        bulk of the card's content.
                    </Card.Text>
                    <Link to={{
                        pathname: `workspace/taskcards/${this.props.card.id}`,
                        state: {
                            card: this.props.card
                        }
                    }}>
                        <Button variant="primary" block className="rounded">Open</Button>
                    </Link>
                </Card.Body>
            </Card>
        )
    }
}