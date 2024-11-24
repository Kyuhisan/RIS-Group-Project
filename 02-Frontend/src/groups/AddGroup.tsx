import React, { useState, ChangeEvent } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

interface Task {
  id: number;
  taskName: string;
  taskDescription: string;
  taskGroup: string;
  status: boolean;
}

interface TaskGroup {
  id: number;
  groupName: string;
  groupProgress: number;
  listOfTasks: Task[];
}

export default function AddGroup() {
  let navigate = useNavigate();

  const [taskGroup, setTaskGroup] = useState<TaskGroup>({
    id: 0,
    groupName: "",
    groupProgress: 0,
    listOfTasks: [],
  });

  const { groupName, groupProgress, listOfTasks } = taskGroup;

  // Typing the event as ChangeEvent for input elements
  const onInputChange = (e: ChangeEvent<HTMLInputElement>) => {
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
          <h2 className="text-center m-4">Create new Group</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="taskName" className="form-label">
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
