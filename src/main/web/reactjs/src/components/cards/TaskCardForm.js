import React from 'react';
import "../Modal.css"
import {Button} from "react-bootstrap";
import Form from "react-bootstrap/Form";

export default class TaskForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            description: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.clearInput = this.clearInput.bind(this);
    }

    render() {
        return (
            <div>
                <Form>
                    <Form.Group> <br/>
                        <Form.Control type="text" onChange={this.handleChange} name="name"
                                      value={this.state.name} placeholder="Enter name"/>
                        <Form.Control type="text" onChange={this.handleChange} name="description"
                                      value={this.state.description} placeholder="Enter description"/>
                    </Form.Group>
                    <Button variant="primary" type="submit" onClick={this.handleSubmit}>Save</Button>
                    {' '}
                    <Button variant="primary" onClick={this.props.onCancel}>Cancel</Button>
                </Form>
            </div>
        )
    }

    handleChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

    handleSubmit(e) {
        e.preventDefault();
        if (this.state.name.length !== 0) {
            const newCard = {
                name: this.state.name,
                description: this.state.description
            };
            this.props.onSubmit(newCard);
            this.clearInput();
            this.props.onCancel();
        }
    }

    clearInput() {
        this.setState(() => ({
            name: '',
            description: ''
        }));
    }
}