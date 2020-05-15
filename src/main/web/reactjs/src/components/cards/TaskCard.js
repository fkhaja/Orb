import React from 'react';
import './TaskCard.css'
import {Link} from "react-router-dom";

export default class TaskCard extends React.Component {

    render() {
        return (
            <Link to={{
                pathname: `workspace/taskcards/${this.props.card.cardId}`,
                state: {
                    card: this.props.card
                }
            }} className="card p-card " style={{"textDecoration": "none"}}>
                <h3 className="h3-card">{this.props.card.name}</h3>
                <p className="small p-card">Card description with lots of great facts and interesting details.</p>
                <div className="go-corner">
                    <div className="go-arrow">
                        →
                    </div>
                </div>
            </Link>
        )
    }
}