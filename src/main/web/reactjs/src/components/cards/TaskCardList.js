import React from 'react';
import TaskCard from "./TaskCard";
import './TaskCardList.css'
import "../Modal.css"
import {deleteTaskCard, saveTaskCard, updateTaskCard} from "../../util/RequestUtils";
import AddCardModal from "./AddCardModal";

export default class TaskCardList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            cards: []
        };
        this.handleShowInputChange = this.handleShowInputChange.bind(this);
        this.handleTaskCardCreate = this.handleTaskCardCreate.bind(this);
        this.handleTaskCardUpdate = this.handleTaskCardUpdate.bind(this);
        this.handleTaskCardDelete = this.handleTaskCardDelete.bind(this);
    }

    componentDidMount() {
        this.setState({cards: this.props.cards});
    }

    render() {
        return (
            <div className="card-container">
                <div className="add_btn" onClick={this.handleShowInputChange}>
                    <button className="icon-btn add-btn">
                        <div className="add-icon"/>
                        <div className="btn-txt">
                            <span>Add card</span>
                        </div>
                    </button>
                </div>

                <div className="card-list">
                    {this.state.cards.map((card, i) => (
                        <TaskCard card={card} key={card.cardId} index={i} onUpdate={this.handleTaskCardUpdate}
                                  onDelete={(i) => this.handleTaskCardDelete(i)}/>
                    ))}
                </div>

                <AddCardModal show={this.state.showModal} onClose={this.handleShowInputChange}
                              onCreate={this.handleTaskCardCreate}/>
            </div>
        )
    }

    handleShowInputChange() {
        this.setState(() => ({showModal: !this.state.showModal}));
    }

    handleTaskCardCreate(card) {
        saveTaskCard(card)
            .then(response => this.setState(() => ({cards: this.state.cards.concat(response)})))
            .catch(console.log);
    }

    handleTaskCardUpdate(card) {
        this.setState({showModal: false});
        updateTaskCard(card).catch(console.log);
    }

    handleTaskCardDelete(index) {
        deleteTaskCard(this.state.cards[index]).catch(console.log);
        let newCards = this.state.cards.slice();
        newCards.splice(index, 1);
        this.setState(() => ({cards: newCards}));
    }
}