import React from 'react';
import axios from "axios";
import ListGroup from "react-bootstrap/ListGroup";

export default class TaskCardList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            taskCards: []
        }
    }

    componentDidMount() {
        this.findAllTaskCardS();
    }

    findAllTaskCardS() {
        axios.get('http://localhost:8080/taskcards')
            .then(response => response.data)
            .then(data => {
                this.setState(state => ({
                    taskCards: data
                }))
            }).catch(e => console.log(e))
    }

    render() {
        return (
            <div>
                <h1 className="text-white">Task Cards</h1>
                <div>
                    <ListGroup className="text-white">
                        {this.state.taskCards.map(card => (
                            <ListGroup.Item className="bg-dark" key={card.id}>
                                <div>
                                    <h2>{card.name} <span style={{"fontSize": "small"}}>({card.creationDate})</span></h2>
                                    <ListGroup className="text-white">
                                        {card.tasks.map(task => (
                                            <ListGroup.Item className="bg-dark" key={card.id}>
                                                #{task.id}: {task.value}
                                            </ListGroup.Item>
                                        ))}
                                    </ListGroup>
                                </div>
                            </ListGroup.Item>
                        ))}
                    </ListGroup>
                </div>
            </div>
        )
    }
}