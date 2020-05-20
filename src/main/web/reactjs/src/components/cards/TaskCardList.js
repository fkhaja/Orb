import React from 'react';
import TaskCard from "./TaskCard";
import './TaskCardList.css'
import {Button} from "react-bootstrap";
import TaskCardForm from "./TaskCardForm";
import {deleteTaskCard, saveTaskCard, updateTaskCard} from "../../util/RequestUtils";

export default class TaskCardList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showInput: false,
            cards: []
        };
        this.handleShowInputChange = this.handleShowInputChange.bind(this);
        this.handleTaskCardCreate = this.handleTaskCardCreate.bind(this);
        this.handleTaskCardUpdate = this.handleTaskCardUpdate.bind(this);
        this.handleTaskCardDelete = this.handleTaskCardDelete.bind(this);
    }

    componentDidMount() {
        this.setState({cards: this.props.cards})
    }

    render() {
        return (
            <div>
                {this.state.showInput ?
                    <TaskCardForm onCancel={this.handleShowInputChange} onSubmit={this.handleTaskCardCreate}/>
                    :
                    <Button variant="primary" className="add__btn" onClick={this.handleShowInputChange}>
                        Add task card
                    </Button>
                }

                <div className="container">
                    {this.state.cards.map((card, i) => (
                        <TaskCard card={card} key={card.cardId} index={i} onUpdate={this.handleTaskCardUpdate}
                                  onDelete={(i) => this.handleTaskCardDelete(i)}/>
                    ))}
                </div>
            </div>
        )
    }

    handleShowInputChange() {
        this.setState(() => ({showInput: !this.state.showInput}));
    }

    handleTaskCardCreate(card) {
        saveTaskCard(card)
            .then(response => this.setState(() => ({cards: this.state.cards.concat(response)})))
            .catch(console.log);
    }

    handleTaskCardUpdate(card) {
        this.setState({showInput: false});
        updateTaskCard(card).catch(console.log);
    }

    handleTaskCardDelete(index) {
        deleteTaskCard(this.state.cards[index]).catch(console.log);
        let newCards = this.state.cards.slice();
        newCards.splice(index, 1);
        this.setState(() => ({cards: newCards}));
    }
}