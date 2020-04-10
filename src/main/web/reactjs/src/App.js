import React, {Component} from 'react';
import Tasks from './components/tasks';

class App extends Component {
  state = {
    tasks: []
  };

  componentDidMount() {
    fetch('http://localhost:8080/tasks')
        .then(res => res.json())
        .then((data) => {
          this.setState({ tasks: data })
        })
        .catch(console.log)
  }
  render() {
    return (
        <Tasks tasks={this.state.tasks} />
    )
  }
}

export default App;