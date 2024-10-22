import React, { useState, ChangeEvent } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

interface Task {
  taskName: string;
  taskDescription: string;
  taskGroup: string;
  taskStatus: boolean;
}

export default function AddTask() {
  let navigate = useNavigate();

  const [task, setTask] = useState<Task>({
    taskName: "",
    taskDescription: "",
    taskGroup: "",
    taskStatus: false,
  });

  const { taskName, taskDescription, taskGroup } = task;

  // Typing the event as ChangeEvent for input elements
  const onInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setTask({ ...task, [name]: value });
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8888/api/tasks", task);
      navigate("/");
    } catch (error) {
      console.error("There was an error creating the task", error);
    }
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Create new Task</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="taskName" className="form-label">
                Task Name
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter Task Name"
                name="taskName"
                value={taskName}
                onChange={onInputChange}
              />
              <label htmlFor="taskDescription" className="form-label">
                Task Description
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter Task Description"
                name="taskDescription"
                value={taskDescription}
                onChange={onInputChange}
              />
              <label htmlFor="taskGroup" className="form-label">
                Task Group
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter Task Group"
                name="taskGroup"
                value={taskGroup}
                onChange={onInputChange}
              />
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Create Task
            </button>
            <Link className="btn btn-outline-danger mx-3" to="/">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}
