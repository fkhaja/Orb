import React from 'react'

const Tasks = ({tasks}) => {
    return (
        <div>
            <h1>Task List</h1>
            {tasks.map((task) => (
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">{task.value}</h5>
                        <h6 class="card-subtitle mb-2 text-muted">{task.expiry}</h6>
                    </div>
                </div>
            ))}
        </div>
    )
};

export default Tasks