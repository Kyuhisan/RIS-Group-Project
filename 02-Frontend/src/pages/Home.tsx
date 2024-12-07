import { useEffect, useState } from "react";
import axios from "axios";
import "../../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Link } from "react-router-dom";

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
  status: TaskStatus;
}

interface TaskGroup {
  id: number;
  groupName: string;
  groupProgress: number;
  listOfTasks: Task[];
  file?: File | null; // Optional file property
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
      LoadTasks();
      LoadGroups();
    } catch (error) {
      console.error("Error deleting task:", error);
    }
  };

  const deleteGroup = async (id: number) => {
    try {
      await axios.delete(`http://localhost:8888/api/group/${id}`);
      LoadGroups();
    } catch (error) {
      console.error("Error deleting group:", error);
    }
  };

  const uploadFile = async (groupId: number, file: File) => {
    const formData = new FormData();
    formData.append("file", file);

    try {
      await axios.post(
        `http://localhost:8888/api/group/${groupId}/upload`,
        formData,
        {
          headers: { "Content-Type": "multipart/form-data" },
        }
      );
      LoadGroups();
    } catch (error) {
      console.error("Error uploading file:", error);
    }
  };

  const handleFileUpload = (
    groupId: number,
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    const file = event.target.files?.[0];
    if (file) {
      uploadFile(groupId, file);
    }
  };

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
        <table className="table table-bordered">
          <thead>
            <tr>
              <th scope="col">Group ID</th>
              <th scope="col">Group Name</th>
              <th scope="col">Group Progress</th>
              <th scope="col">Group Tasks</th>
              <th scope="col">Group Actions</th>
              <th scope="col">File Status</th>
            </tr>
          </thead>
          <tbody>
            {filteredGroups.length > 0 ? (
              filteredGroups.map((group) => {
                const totalTasks = group.listOfTasks.length;
                const finishedTasks = group.listOfTasks.filter(
                  (task) => task.status === TaskStatus.FINISHED
                ).length;
                const progressPercentage =
                  totalTasks > 0 ? (finishedTasks / totalTasks) * 100 : 0;

                return (
                  <tr className="align-middle" key={group.id}>
                    <th scope="row">{group.id}</th>
                    <td>{group.groupName}</td>
                    <td>
                      <div className="mb-2">
                        <div className="progress" style={{ height: "20px" }}>
                          <div
                            className={`progress-bar ${
                              progressPercentage === 100
                                ? "bg-success"
                                : progressPercentage > 50
                                ? "bg-warning"
                                : "bg-danger"
                            }`}
                            role="progressbar"
                            style={{
                              width: `${progressPercentage.toFixed(2)}%`,
                            }}
                            aria-valuenow={progressPercentage}
                            aria-valuemin={0}
                            aria-valuemax={100}
                          >
                            {progressPercentage.toFixed(0)}%
                          </div>
                        </div>
                      </div>
                    </td>

                    <td className="col-md-6">
                      <table className="table table-striped text-center table-sm ">
                        <thead>
                          <tr>
                            <th scope="col">Task Name</th>
                            <th scope="col">Status</th>
                            <th scope="col">Description</th>
                            <th scope="col">Actions</th>
                          </tr>
                        </thead>
                        <tbody className="align-middle">
                          {group.listOfTasks.map((task) => (
                            <tr key={task.id}>
                              <td>{task.taskName}</td>
                              <td>{task.status}</td>
                              <td>{task.taskDescription}</td>
                              <td>
                                <div className="d-inline-flex justify-content-end">
                                  <button
                                    className="btn btn-danger btn-danger mx-2"
                                    onClick={() => deleteTask(task.id)}
                                  >
                                    Delete
                                  </button>
                                </div>
                              </td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </td>

                    <td>
                      <Link
                        className="btn btn-outline-primary"
                        to={`/AddTask/${group.id}`}
                      >
                        +
                      </Link>
                      <Link
                        className="btn btn-outline-primary mx-2"
                        to={`/EditGroup/${group.id}`}
                      >
                        Edit
                      </Link>
                      <button
                        className="btn btn-danger"
                        onClick={() => deleteGroup(group.id)}
                      >
                        Delete
                      </button>
                      <label className="btn btn-outline-secondary mx-2">
                        <input
                          type="file"
                          hidden
                          onChange={(e) => handleFileUpload(group.id, e)}
                        />
                        <i className="bi bi-upload"></i> Upload
                      </label>
                    </td>

                    <td>
                      {group.file ? (
                        <i className="bi bi-file-earmark-check text-success"></i>
                      ) : null}
                    </td>
                  </tr>
                );
              })
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
