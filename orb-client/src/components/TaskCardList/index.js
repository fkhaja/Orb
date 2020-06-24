import React, {useEffect, useState} from "react";
import TaskCard from "./TaskCard";
import "./style.css";
import "../../styles/modal.css";
import AddCardModal from "./AddCardModal";
import {showCreateModal} from "../../redux/modal/actions";
import {fetchCards} from "../../redux/cards/actions";
import {useDispatch, useSelector} from "react-redux";
import Loader from "../App/Loader";

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
            {cards.length > 0 ?
                <div>
                    <div className="add_btn">
                        <button className="icon-btn add-btn" onClick={() => dispatch(showCreateModal())}>
                            <div className="add-icon"/>
                            <div className="btn-txt">
                                <span>Add card</span>
                            </div>
                        </button>
                    </div>
                    <div className="card-list">
                        {cards.sort((a, b) => b.cardId - a.cardId).map((card, i) => (
                            <TaskCard card={card} key={card.cardId} index={++i}/>
                        ))}
                    </div>
                    {loading &&
                    <div className="loader-container">
                        <Loader/>
                    </div>}
                </div>
                :
                <div className="no-card">
                    {"" + filter === "" + (card => card) ?
                        <div>
                            <span>You don't have any cards yet</span>
                            <div className="no-card-btn">
                                <button className="add-first-btn" onClick={() => dispatch(showCreateModal())}>
                                    Create one
                                </button>
                            </div>
                        </div>
                        :
                        <span>No cards matching the condition</span>
                    }
                </div>
            }
        </div>
    )
};

export default TaskCardList;