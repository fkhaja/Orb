import React, {Fragment} from 'react';
import "./TaskList.css";

export default class Task extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            completed: this.props.task.completed
        };

        this.onCompleted = this.onCompleted.bind(this);
    }

    componentDidMount() {
        this.setState(() => ({
            completed: this.props.task.completed
        }));
    }

    render() {
        return (
            <Fragment key={this.props.task.id}>
                <input id={this.props.id} type="checkbox" name="check"
                       onChange={this.onCompleted}
                       checked={this.state.completed}/>
                <label htmlFor={this.props.id}>{this.props.task.value}</label>
            </Fragment>
        )
    }

    onCompleted() {
        this.setState({completed: !this.state.completed}, () => {
            this.props.task.completed = this.state.completed;
            this.props.onCompleted(this.props.task);
        });
    }
}