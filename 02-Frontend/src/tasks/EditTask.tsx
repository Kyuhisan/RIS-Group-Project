import React, { useState, ChangeEvent, useEffect } from "react";
import axios from "axios";
import { Link, useNavigate, useParams } from "react-router-dom";

interface Task {
  taskName: string;
  taskDescription: string;
  taskGroup: string;
  taskStatus: boolean;
}

export default function EditTask() {
  let navigate = useNavigate();

  const { id } = useParams();

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

  useEffect(() => {
    loadTask();
  }, []);

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8888/api/task/${id}`, task);
      navigate("/");
    } catch (error) {
      console.error("There was an error creating the task", error);
    }
  };

  const loadTask = async () => {
    const result = await axios.get(`http://localhost:8888/api/task/${id}`);
    setTask(result.data);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Edit Task</h2>

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
              Edit Task
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
