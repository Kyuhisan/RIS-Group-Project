import React, { useState, useEffect, ChangeEvent } from "react";
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

export default function AddTask() {
  let navigate = useNavigate();
  const { groupId } = useParams(); // Get group ID from URL parameters

  const [task, setTask] = useState<Task>({
    id: 0,
    taskName: "",
    taskDescription: "",
    taskGroup: null,
    taskStatus: TaskStatus.NOT_STARTED,
  });

  const [group, setGroup] = useState<TaskGroup | null>(null); // Store the group details

  useEffect(() => {
    if (groupId) {
      loadGroup(parseInt(groupId)); // Load the group details using the groupId
    }
  }, [groupId]);

  const loadGroup = async (id: number) => {
    try {
      const result = await axios.get(`http://localhost:8888/api/group/${id}`);
      setGroup(result.data);
      setTask((prevTask) => ({
        ...prevTask,
        taskGroup: result.data, // Assign group to task
      }));
    } catch (error) {
      console.error("Error loading group:", error);
    }
  };

  const onInputChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setTask({ ...task, [name]: value });
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8888/api/tasks", task);
      navigate("/");
    } catch (error) {
      console.error("There was an error creating the task:", error);
    }
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Create New Task</h2>

          {group ? (
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
                <input
                  type="text"
                  className="form-control"
                  value={group.groupName}
                  disabled // Display the group name, but make it non-editable
                />
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
                Create Task
              </button>
              <Link className="btn btn-outline-danger mx-3" to="/">
                Cancel
              </Link>
            </form>
          ) : (
            <p>Loading group details...</p>
          )}
        </div>
      </div>
    </div>
  );
}
