import React, {useEffect} from 'react';
import TaskCard from "./TaskCard";
import './TaskCardList.css'
import "../../styles/Modal.css"
import AddCardModal from "./AddCardModal";
import {showCreateModal} from "../../redux/actions/modalActions";
import {fetchCards} from "../../redux/actions/cardActions";
import {useDispatch, useSelector} from "react-redux";

const TaskCardList = () => {
    const dispatch = useDispatch();
    const filter = useSelector(state => state.filters.cardFilter);
    const cards = useSelector(state => state.cards.fetchedCards).filter(filter);

    useEffect(() => {
        dispatch(fetchCards());
    }, [dispatch]);

    return (
        <div className="card-container">
            <AddCardModal/>
            <div className="add_btn">
                <button className="icon-btn add-btn" onClick={() => dispatch(showCreateModal())}>
                    <div className="add-icon"/>
                    <div className="btn-txt">
                        <span>Add card</span>
                    </div>
                </button>
            </div>

            {cards.length > 0 &&
            <div className="card-list">
                {cards.map((card, i) => (
                    <TaskCard card={card} key={card.cardId} index={++i}/>
                ))}
            </div>}
        </div>
    )
};

export default TaskCardList;