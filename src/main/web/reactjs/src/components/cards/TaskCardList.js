import React from 'react';
import TaskCard from "./TaskCard";
import './TaskCardList.css'

export default class TaskCardList extends React.Component {

    render() {
        return (
            <div className="container">
                {this.props.cards.map(card => (
                    <TaskCard card={card} key={card.cardId}/>
                ))}
            </div>
        )
    }
}