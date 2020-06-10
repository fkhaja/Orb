import React, {useState} from "react";
import "../../styles/Modal.css";
import "../../styles/InputText.css";
import {useDispatch, useSelector} from "react-redux";
import {hideCreateModal} from "../../redux/actions/modalActions";
import {createCard} from "../../redux/actions/cardActions";

const AddCardModal = () => {
    const dispatch = useDispatch();
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const showModal = useSelector(state => state.modal.showCreateModal);

    const handleNameChange = event => setName(event.target.value);
    const handleDescriptionChange = event => setDescription(event.target.value);

    const handleSubmit = event => {
        event.preventDefault();

        if (name.length) {
            const newCard = {
                name: name,
                description: description
            };
            dispatch(createCard(newCard));
            dispatch(hideCreateModal());
            clearInput();
        }
    }

    const clearInput = () => {
        setName("");
        setDescription("");
    }

    return (
        <div id="modal-overlay-container-0 active" className={`modal-overlay ${showModal && "active"}`}>
            <div id="modal-div-0" className={`modal ${showModal && "active"}`}>
                <p className="close-modal" onClick={() => dispatch(hideCreateModal())}>
                    <svg viewBox="0 0 20 20">
                        <path fill="#8e54e9"
                              d="M15.898,4.045c-0.271-0.272-0.713-0.272-0.986,0l-4.71,4.711L5.493,4.045c-0.272-0.272-0.714-0.272-0.986,0s-0.272,0.714,0,0.986l4.709,4.711l-4.71,4.711c-0.272,0.271-0.272,0.713,0,0.986c0.136,0.136,0.314,0.203,0.492,0.203c0.179,0,0.357-0.067,0.493-0.203l4.711-4.711l4.71,4.711c0.137,0.136,0.314,0.203,0.494,0.203c0.178,0,0.355-0.067,0.492-0.203c0.273-0.273,0.273-0.715,0-0.986l-4.711-4.711l4.711-4.711C16.172,4.759,16.172,4.317,15.898,4.045z"/>
                    </svg>
                </p>
                <div className="modal-content">
                    <h2 className="title">Create task card</h2>
                    <div className="modal_task_list">
                        <div>
                            <div className="input-wrapper">
                                <form>
                                    <div className="group">
                                        <input type="text" required="required" onChange={handleNameChange}
                                               name="name" value={name}/>
                                        <span className="highlight"/>
                                        <span className="bar"/>
                                        <label>Name</label>
                                    </div>
                                    <div className="group">
                                        <input type="text" required="required" onChange={handleDescriptionChange}
                                               name="description" value={description}/>
                                        <span className="highlight"/>
                                        <span className="bar"/>
                                        <label>Description</label>
                                    </div>
                                    <button className="bttn-dark sumbit" onClick={handleSubmit}>
                                        Create
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AddCardModal;