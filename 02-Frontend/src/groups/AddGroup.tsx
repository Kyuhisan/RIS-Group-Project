import React, { useState, ChangeEvent } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

enum TaskStatus {
  IN_PROGRESS = "IN_PROGRESS",
  FINISHED = "FINISHED",
  NOT_STARTED = "NOT_STARTED",
}

enum Period {
  DAILY = "DAILY",
  WEEKLY = "WEEKLY",
  MONTHLY = "MONTHLY",
}

interface Task {
  id: number;
  taskName: string;
  taskDescription: string;
  taskGroup: TaskGroup | null;
  status: TaskStatus;
}

interface TaskGroup {
  id: number;
  groupName: string;
  groupProgress: number;
  listOfTasks: Task[];
  period?: Period | null;
  creationDate: string; // Add creationDate field
}

export default function AddGroup() {
  let navigate = useNavigate();

  const getLocalDate = () => {
    const now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset()); // Adjust to local timezone
    return now.toISOString().split("T")[0]; // Extract date in YYYY-MM-DD format
  };

  const [taskGroup, setTaskGroup] = useState<TaskGroup>({
    id: 0,
    groupName: "",
    groupProgress: 0,
    listOfTasks: [],
    period: null, // Default to null
    creationDate: getLocalDate(), // Set to today's date in local timezone
  });

  const { groupName, period } = taskGroup;

  const onInputChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setTaskGroup({ ...taskGroup, [name]: value });
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8888/api/group", taskGroup);
      navigate("/");
    } catch (error) {
      console.error("There was an error creating the group.", error);
    }
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Create New Group</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="groupName" className="form-label">
                Group Name
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter Group Name"
                name="groupName"
                value={groupName}
                onChange={onInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="period" className="form-label">
                Repeat Period
              </label>
              <select
                className="form-control"
                name="period"
                value={period || ""}
                onChange={onInputChange}
              >
                <option value="">Select a period</option>
                <option value={Period.DAILY}>Daily</option>
                <option value={Period.WEEKLY}>Weekly</option>
                <option value={Period.MONTHLY}>Monthly</option>
              </select>
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Create Group
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
