import React from 'react';
import CardDeck from "react-bootstrap/CardDeck";
import Row from "react-bootstrap/Row";
import TaskCard from "./TaskCard";

export default class TaskCardList extends React.Component {

    render() {
        return (
            <div style={{"margin": "0 auto", "width": "70%" }}>
                <CardDeck style={{"width": "85%"}}>
                    {this.splitTo(this.props.cards, 4).map(row => (
                        <div className="text-md-center">
                            <Row style={{"margin": "15px"}}>
                                {row.map(card => (
                                        <TaskCard name={card.name}/>
                                    )
                                )}
                            </Row>
                        </div>
                    ))}
                </CardDeck>
            </div>
        )
    }


    splitTo(arr, n) {
        let subArray = [];
        for (let i = 0; i < Math.ceil(arr.length / n); i++) {
            subArray[i] = arr.slice((i * n), (i * n) + n);
        }

        return subArray;
    }
}