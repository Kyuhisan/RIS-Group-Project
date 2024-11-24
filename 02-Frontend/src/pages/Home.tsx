import { useEffect, useState } from "react";
import axios from "axios";
import "../../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Link } from "react-router-dom";

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

export default function Home() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [groups, setTaskGroup] = useState<TaskGroup[]>([]);
  const [search, setSearch] = useState<string>("");

  useEffect(() => {
    LoadTasks();
    LoadGroups();
  }, []);

  const LoadTasks = async () => {
    try {
      const result = await axios.get("http://localhost:8888/api/tasks");
      setTasks(result.data);
    } catch (error) {
      console.error("Error loading tasks:", error);
    }
  };

  const LoadGroups = async () => {
    try {
      const result = await axios.get("http://localhost:8888/api/groups");
      setTaskGroup(result.data);
    } catch (error) {
      console.error("Error loading groups:", error);
    }
  };

  const deleteTask = async (id: number) => {
    try {
      await axios.delete(`http://localhost:8888/api/task/${id}`);
      LoadTasks(); // Reload tasks after deletion
    } catch (error) {
      console.error("Error deleting task:", error);
    }
  };

  const deleteGroup = async (id: number) => {
    try {
      await axios.delete(`http://localhost:8888/api/group/${id}`);
      LoadGroups(); // Reload tasks after deletion
    } catch (error) {
      console.error("Error deleting group:", error);
    }
  };

  const filteredTasks = tasks.filter((task) => {
    const taskValues = `${task.id} ${task.taskName} ${task.taskDescription} ${
      task.taskGroup
    } ${task.status ? "Completed" : "Pending"}`.toLowerCase();
    return taskValues.includes(search.toLowerCase());
  });

  const filteredGroups = groups.filter((group) => {
    const groupValues =
      `${group.id} ${group.groupName} ${group.groupProgress} ${group.listOfTasks}`.toLowerCase();
    return groupValues.includes(search.toLowerCase());
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
          onChange={(e) => setSearch(e.target.value)}
        />

        <Link className="btn btn-outline-primary" to="/AddGroup">
          Create Group
        </Link>
      </div>

      <div id="groupsContainer">
        <table className="table">
          <thead>
            <tr>
              <th scope="col">Group ID</th>
              <th scope="col">Group Name</th>
              <th scope="col">Group Progress</th>
              <th scope="col">Group Tasks</th>
              <th scope="col">Group Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredGroups.length > 0 ? (
              filteredGroups.map((group) => (
                <tr key={group.id}>
                  <th scope="row">{group.id}</th>
                  <td>{group.groupName}</td>
                  <td>
                    {(Math.round(group.groupProgress * 100) / 100).toFixed(2)}
                  </td>
                  <td>
                    {group.listOfTasks.map((task) => (
                      <li key={task.id}>
                        {task.taskName} {task.status}
                      </li>
                    ))}
                  </td>
                  <td>
                    <Link
                      className="btn btn-outline-primary mx-2"
                      to={`/EditGroup/${group.id}`}
                    >
                      Edit
                    </Link>
                    <button
                      className="btn btn-danger mx-2"
                      onClick={() => deleteGroup(group.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={6} className="text-center">
                  No matching groups found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
