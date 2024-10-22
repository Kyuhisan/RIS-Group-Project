import React, { useEffect, useState } from "react";
import axios from "axios";
import "../../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Link } from "react-router-dom";

// Define the task type to enforce type safety
interface Task {
  id: number;
  taskName: string;
  taskDescription: string;
  taskGroup: string;
  status: boolean;
}

export default function Home() {
  const [tasks, setTasks] = useState<Task[]>([]); // Type for tasks array
  const [search, setSearch] = useState<string>(""); // Search input

  useEffect(() => {
    LoadTasks();
  }, []);

  // Load tasks from the API
  const LoadTasks = async () => {
    try {
      const result = await axios.get("http://localhost:8888/api/tasks");
      setTasks(result.data);
    } catch (error) {
      console.error("Error loading tasks:", error);
    }
  };

  // Load a single task based on the ID
  const loadTask = async (id: string) => {
    try {
      const result = await axios.get(`http://localhost:8888/api/task/${id}`);
      setTasks([result.data]); // Set to an array with a single task
    } catch (error) {
      console.error("Error loading task:", error);
    }
  };

  // Delete task by ID
  const deleteTask = async (id: number) => {
    try {
      await axios.delete(`http://localhost:8888/api/task/${id}`);
      LoadTasks(); // Reload tasks after deletion
    } catch (error) {
      console.error("Error deleting task:", error);
    }
  };

  // Filter tasks based on the search term, checking all task properties
  const filteredTasks = tasks.filter((task) => {
    // Combine all properties into one string and search for the search term within it
    const taskValues = `${task.id} ${task.taskName} ${task.taskDescription} ${
      task.taskGroup
    } ${task.status ? "Completed" : "Pending"}`.toLowerCase();
    return taskValues.includes(search.toLowerCase());
  });

  return (
    <div className="container">
      <h1>Advanced TO-DO List</h1>

      {/* Search Input */}
      <div className="input-group rounded mb-3">
        <input
          type="search"
          className="form-control rounded"
          placeholder="Search tasks (by any field)"
          aria-label="Search"
          aria-describedby="search-addon"
          value={search}
          onChange={(e) => setSearch(e.target.value)} // Update search state on input change
        />
      </div>

      <div id="groupsContainer">
        <table className="table">
          <thead>
            <tr>
              <th scope="col">Task ID</th>
              <th scope="col">Task Name</th>
              <th scope="col">Task Description</th>
              <th scope="col">Task Group</th>
              <th scope="col">Task Status</th>
              <th scope="col">Task Action</th>
            </tr>
          </thead>
          <tbody>
            {filteredTasks.length > 0 ? (
              filteredTasks.map((task) => (
                <tr key={task.id}>
                  <th scope="row">{task.id}</th>
                  <td>{task.taskName}</td>
                  <td>{task.taskDescription}</td>
                  <td>{task.taskGroup}</td>
                  <td>{task.status ? "Completed" : "Pending"}</td>
                  <td>
                    <Link
                      className="btn btn-outline-primary mx-2"
                      to={`/EditTask/${task.id}`}
                    >
                      Edit
                    </Link>
                    <button
                      className="btn btn-danger mx-2"
                      onClick={() => deleteTask(task.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={6} className="text-center">
                  No matching tasks found.
                </td>
              </tr>
            )}
          </tbody>
        </table>

        <Link className="btn btn-outline-primary mx-2 my-4" to="/AddTask">
          Add Task
        </Link>
      </div>
    </div>
  );
}
