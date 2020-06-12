import React, {useEffect, useState} from 'react';
import TaskCard from "./TaskCard";
import './TaskCardList.css'
import "../../styles/Modal.css"
import AddCardModal from "./AddCardModal";
import {showCreateModal} from "../../redux/actions/modalActions";
import {fetchCards} from "../../redux/actions/cardActions";
import {useDispatch, useSelector} from "react-redux";
import Loader from "../common/Loader";

const TaskCardList = () => {
    const dispatch = useDispatch();
    const filter = useSelector(state => state.filters.cardFilter);
    const cards = useSelector(state => state.cards.fetchedCards).filter(filter);
    const totalPages = useSelector(state => state.cards.totalPages);
    const loading = useSelector(state => state.app.showLoader);
    const [page, setPage] = useState(0);

    useEffect(() => {
        dispatch(fetchCards(page));
    }, [dispatch, page]);

    window.onscroll = () => {
        if (window.innerHeight + window.scrollY === document.body.scrollHeight && page <= (totalPages - 2)) {
            setPage(page + 1);
        }
    };

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

            {loading &&
            <div className="loader-container">
                <Loader/>
            </div>
            }
        </div>
    )
};

export default TaskCardList;