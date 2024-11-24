import React, { useState, ChangeEvent, useEffect } from "react";
import axios from "axios";
import { Link, useNavigate, useParams } from "react-router-dom";

enum TaskStatus {
  IN_PROGRESS = "IN_PROGRESS",
  FINISHED = "FINISHED",
  NOT_STARTED = "NOT_STARTED",
}

interface Task {
  id: number;
  taskName: string;
  taskDescription: string;
  taskGroup: TaskGroup | null;
  taskStatus: TaskStatus;
}

interface TaskGroup {
  id: number;
  groupName: string;
  groupProgress: number;
  listOfTasks: Task[];
}

export default function EditTask() {
  let navigate = useNavigate();
  const { id } = useParams();

  const [task, setTask] = useState<Task>({
    id: 0,
    taskName: "",
    taskDescription: "",
    taskGroup: null,
    taskStatus: TaskStatus.NOT_STARTED,
  });

  const [groups, setGroups] = useState<TaskGroup[]>([]); // Store task groups

  useEffect(() => {
    loadTask();
    loadTaskGroups(); // Load all task groups
  }, []);

  const loadTask = async () => {
    try {
      const result = await axios.get(`http://localhost:8888/api/task/${id}`);
      setTask(result.data);
    } catch (error) {
      console.error("Error loading task:", error);
    }
  };

  const loadTaskGroups = async () => {
    try {
      const result = await axios.get("http://localhost:8888/api/groups");
      setGroups(result.data);
    } catch (error) {
      console.error("Error loading task groups:", error);
    }
  };

  const onInputChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;

    if (name === "taskGroup") {
      // Find the selected task group by ID
      const selectedGroup = groups.find(
        (group) => group.id === parseInt(value)
      );
      setTask({ ...task, taskGroup: selectedGroup || null });
    } else {
      setTask({ ...task, [name]: value });
    }
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8888/api/task/${id}`, task);
      navigate("/");
    } catch (error) {
      console.error("There was an error updating the task:", error);
    }
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
                type="text"
                className="form-control"
                placeholder="Enter Task Name"
                name="taskName"
                value={task.taskName}
                onChange={onInputChange}
              />
              <label htmlFor="taskDescription" className="form-label">
                Task Description
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter Task Description"
                name="taskDescription"
                value={task.taskDescription}
                onChange={onInputChange}
              />
              <label htmlFor="taskGroup" className="form-label">
                Task Group
              </label>
              <select
                className="form-select"
                name="taskGroup"
                value={task.taskGroup?.id || ""}
                onChange={onInputChange}
              >
                <option value="">Select a group</option>
                {groups.map((group) => (
                  <option key={group.id} value={group.id}>
                    {group.groupName}
                  </option>
                ))}
              </select>
              <label htmlFor="taskStatus" className="form-label">
                Status
              </label>
              <select
                className="form-select"
                name="taskStatus"
                value={task.taskStatus}
                onChange={onInputChange}
              >
                {Object.values(TaskStatus).map((status) => (
                  <option key={status} value={status}>
                    {status.replace("_", " ")}
                  </option>
                ))}
              </select>
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
