const API_URL = 'http://localhost:8080/api/tasks';

// --- READ (GET) ---
async function fetchTasks() {
    const response = await fetch(API_URL);
    const tasks = await response.json();
    displayTasks(tasks);
}

function displayTasks(tasks) {
    const container = document.getElementById('tasks-container');
    container.innerHTML = ''; // Clear existing tasks

    tasks.forEach(task => {
        const card = document.createElement('div');
        let cardClass = '';
        if (task.status === 'COMPLETED') {
            cardClass = 'completed';
        } else if (new Date(task.date + 'T' + task.time) < new Date()) {
            cardClass = 'overdue';
        }

        card.className = `task-card ${cardClass}`;
        card.innerHTML = `
            <h3>${task.title} (ID: ${task.id})</h3>
            <p>Due: ${task.date} at ${task.time}</p>
            <p>Status: <strong>${task.status}</strong></p>
            <p>${task.description}</p>
            <button onclick="completeTask(${task.id})">Mark Complete</button>
            <button onclick="deleteTask(${task.id})">Delete</button>
        `;
        container.appendChild(card);
    });
}

// --- CREATE (POST) ---
document.getElementById('add-task-form').addEventListener('submit', async function(e) {
    e.preventDefault();

    const newTask = {
        title: document.getElementById('title').value,
        description: document.getElementById('description').value,
        date: document.getElementById('date').value,
        time: document.getElementById('time').value,
        status: 'PENDING'
    };

    const response = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTask)
    });

    if (response.ok) {
        alert('Task added successfully!');
        fetchTasks(); // Reload the list
        this.reset(); // Clear the form
    } else {
        const error = await response.text();
        alert('Failed to add task: ' + error);
    }
});

// --- UPDATE (Completion Logic - Placeholder, requires PUT endpoint) ---
// For now, let's keep it simple and just delete for demonstration.
function completeTask(id) {
    alert('Feature coming soon: Need to implement PUT /api/tasks/{id}/complete');
}

// --- DELETE (DELETE) ---
async function deleteTask(id) {
    if (confirm(`Are you sure you want to delete Task ID ${id}?`)) {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (response.status === 204) {
            alert('Task deleted.');
            fetchTasks(); // Reload the list
        } else {
            alert('Failed to delete task.');
        }
    }
}

// Load tasks when the page loads
fetchTasks();