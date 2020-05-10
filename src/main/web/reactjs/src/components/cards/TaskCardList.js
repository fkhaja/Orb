import React from 'react';
import CardDeck from "react-bootstrap/CardDeck";
import Row from "react-bootstrap/Row";
import TaskCard from "./TaskCard";

export default class TaskCardList extends React.Component {

    render() {
        let rowSize = 4;
        let rowCount = 0;

        return (
            <div>
                <CardDeck>
                    {this.splitTo(this.props.cards, rowSize).map(row => (
                        <div className="text-md-center" key={rowCount++}>
                            <Row style={{"margin": "15px"}}>
                                {row.map(card => (
                                        <TaskCard card={card} key={card.cardId}/>
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