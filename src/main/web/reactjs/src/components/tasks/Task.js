import React, {Fragment} from 'react';

export default class Task extends React.Component {

    constructor(props) {
        super(props);
        this.onCompleted = this.onCompleted.bind(this);
    }

    render() {
        return (
            <Fragment key={this.props.task.id}>
                <input id={this.props.id} type="checkbox" name="check"
                       onChange={this.onCompleted}
                       checked={this.props.task.completed}/>
                <label htmlFor={this.props.id}>{this.props.task.value}</label>
            </Fragment>
        )
    }

    onCompleted() {
        this.props.task.completed = !this.props.task.completed;
        this.props.onCompleted(this.props.task);
    }
}