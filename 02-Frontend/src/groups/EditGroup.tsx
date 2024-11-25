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
  status: TaskStatus;
}

interface Group {
  groupName: string;
  groupProgress: number;
  listOfTasks: Task[];
}

export default function EditGroup() {
  const navigate = useNavigate();
  const { id } = useParams();

  const [group, setGroup] = useState<Group>({
    groupName: "",
    groupProgress: 0,
    listOfTasks: [],
  });

  const { groupName, groupProgress, listOfTasks } = group;

  // Handle input changes for group properties
  const onInputChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setGroup({
      ...group,
      [name]: name === "groupProgress" ? Number(value) : value,
    });
  };

  // Handle task updates in the group
  const onTaskChange = (taskId: number, key: string, value: any) => {
    const updatedTasks = listOfTasks.map((task) =>
      task.id === taskId ? { ...task, [key]: value } : task
    );
    setGroup({ ...group, listOfTasks: updatedTasks });
  };

  useEffect(() => {
    loadGroup();
  }, []);

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8888/api/group/${id}`, group);
      navigate("/");
    } catch (error) {
      console.error("There was an error updating the group", error);
    }
  };

  const loadGroup = async () => {
    try {
      const result = await axios.get(`http://localhost:8888/api/group/${id}`);
      setGroup(result.data);
    } catch (error) {
      console.error("Error loading group:", error);
    }
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-8 offset-md-2 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Edit Group</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="groupName" className="form-label">
                Group Name
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter Group Name"
                name="groupName"
                value={groupName}
                onChange={onInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="groupProgress" className="form-label">
                Group Progress
              </label>
              <input
                type="number"
                className="form-control"
                placeholder="Enter Group Progress"
                name="groupProgress"
                value={groupProgress}
                onChange={onInputChange}
              />
            </div>
            <div className="mb-3">
              <h4>Tasks</h4>
              {listOfTasks.map((task) => (
                <div key={task.id} className="border p-3 mb-3">
                  <div className="mb-2">
                    <label className="form-label">Task Name</label>
                    <input
                      type="text"
                      className="form-control"
                      value={task.taskName}
                      onChange={(e) =>
                        onTaskChange(task.id, "taskName", e.target.value)
                      }
                    />
                  </div>
                  <div className="mb-2">
                    <label className="form-label">Task Description</label>
                    <input
                      type="text"
                      className="form-control"
                      value={task.taskDescription}
                      onChange={(e) =>
                        onTaskChange(task.id, "taskDescription", e.target.value)
                      }
                    />
                  </div>
                  <div className="mb-2">
                    <label className="form-label">Status</label>
                    <select
                      className="form-select"
                      value={task.status}
                      onChange={(e) =>
                        onTaskChange(task.id, "status", e.target.value)
                      }
                    >
                      {Object.values(TaskStatus).map((status) => (
                        <option key={status} value={status}>
                          {status.replace("_", " ")}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
              ))}
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Save Group
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
