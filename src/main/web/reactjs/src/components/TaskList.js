import React from 'react';
import axios from 'axios';

export default class TaskList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            tasks: []
        }
    }

    componentDidMount() {
        this.findAllTasks();
    }

    findAllTasks() {
        axios.get('http://localhost:8080/tasks')
            .then(response => response.data)
            .then(data => {
                this.setState(state => ({
                    tasks: data
                }))
            })
    }

    render() {
        return (
            <div className="text-white">
                <table>
                    <thead>
                        <tr>
                            <th>Value</th>
                            <th>Expiry</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.tasks.map(task => (
                            <tr key={task.id}>
                                <td>{task.value}</td>
                                <td>{task.expiry}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        )
    }
}