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
  fileBlob?: string | null; // Base64 string
  fileName?: string | null; // File name
}

export default function Home() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [groups, setTaskGroup] = useState<TaskGroup[]>([]);
  const [search, setSearch] = useState<string>("");
  const [expandedTaskIds, setExpandedTaskIds] = useState<number[]>([]);

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
    const reader = new FileReader();

    reader.onload = async () => {
      const fileBlob = reader.result as string;
      const fileName = file.name;

      const taskGroupUpdate = {
        fileBlob: fileBlob.split(",")[1],
        fileName,
      };

      try {
        await axios.patch(
          `http://localhost:8888/api/group/${groupId}/file`,
          taskGroupUpdate,
          { headers: { "Content-Type": "application/json" } }
        );
        LoadGroups();
      } catch (error) {
        console.error("Error uploading file:", error);
      }
    };

    reader.onerror = () => {
      console.error("Error reading the file.");
    };

    reader.readAsDataURL(file);
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

  const downloadFile = async (groupId: number) => {
    try {
      const response = await axios.get(
        `http://localhost:8888/api/group/${groupId}`,
        { responseType: "json" } // Adjust if binary response is required
      );
      const { fileBlob, fileName } = response.data;

      if (fileBlob && fileName) {
        // Decode the Base64 fileBlob
        const link = document.createElement("a");
        link.href = `data:application/octet-stream;base64,${fileBlob}`;
        link.download = fileName;
        link.click();
      } else {
        alert("No file available to download for this group.");
      }
    } catch (error) {
      console.error("Error downloading file:", error);
      alert("Failed to download the file.");
    }
  };

  const toggleTaskDescription = (taskId: number) => {
    setExpandedTaskIds((prev) =>
      prev.includes(taskId)
        ? prev.filter((id) => id !== taskId)
        : [...prev, taskId]
    );
  };

  const filteredGroups = groups.filter((group) => {
    const groupValues =
      `${group.id} ${group.groupName} ${group.groupProgress}`.toLowerCase();
    const taskMatches = group.listOfTasks.some((task) =>
      `${task.taskName} ${task.status} ${task.taskDescription}`
        .toLowerCase()
        .includes(search.toLowerCase())
    );
    return groupValues.includes(search.toLowerCase()) || taskMatches;
  });

  return (
    <div className="container mt-4">
      <div className="row mb-4">
        <div className="col-md-9">
          <input
            type="search"
            className="form-control"
            placeholder="Search tasks (by any field)"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </div>
        <div className="col-md-3 text-md-end mt-2 mt-md-0">
          <Link className="btn btn-primary w-100" to="/AddGroup">
            Create Group
          </Link>
        </div>
      </div>

      <div className="row">
        {filteredGroups.length > 0 ? (
          filteredGroups.map((group) => {
            const totalTasks = group.listOfTasks.length;
            const finishedTasks = group.listOfTasks.filter(
              (task) => task.status === TaskStatus.FINISHED
            ).length;
            const progressPercentage =
              totalTasks > 0 ? (finishedTasks / totalTasks) * 100 : 0;

            return (
              <div className="col-lg-6 col-xl-4 mb-4" key={group.id}>
                <div className="card h-100 shadow-sm">
                  <div className="card-body">
                    <h5 className="card-title">{group.groupName}</h5>
                    <p className="card-text">
                      Progress:{" "}
                      <span className="fw-bold">
                        {progressPercentage.toFixed(0)}%
                      </span>
                    </p>
                    <div className="progress mb-3" style={{ height: "10px" }}>
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
                      ></div>
                    </div>
                    <p>
                      <strong>Tasks:</strong>
                    </p>
                    <div className="table-responsive">
                      <table className="table table-bordered table-sm">
                        <thead>
                          <tr>
                            <th>Task Name</th>
                            <th>Status</th>
                            <th>Description</th>
                            <th>Actions</th>
                          </tr>
                        </thead>
                        <tbody>
                          {group.listOfTasks.map((task) => (
                            <tr key={task.id}>
                              <td>{task.taskName}</td>
                              <td>{task.status}</td>
                              <td>
                                <button
                                  className="btn btn-sm btn-outline-info"
                                  onClick={() => toggleTaskDescription(task.id)}
                                >
                                  {expandedTaskIds.includes(task.id)
                                    ? "Hide"
                                    : "Show"}
                                </button>
                                {expandedTaskIds.includes(task.id) && (
                                  <div className="mt-2">
                                    {task.taskDescription}
                                  </div>
                                )}
                              </td>
                              <td>
                                <button
                                  className="btn btn-sm btn-outline-danger"
                                  onClick={() => deleteTask(task.id)}
                                >
                                  Delete
                                </button>
                              </td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </div>
                  </div>
                  <div className="card-footer d-flex justify-content-between">
                    <span>
                      {group.fileBlob && group.fileBlob.length > 0 ? (
                        <i className="bi bi-file-earmark-check text-success"></i>
                      ) : (
                        "No attachments"
                      )}
                    </span>
                    <div>
                      <label className="btn btn-sm btn-outline-secondary me-2">
                        <input
                          type="file"
                          hidden
                          onChange={(e) => handleFileUpload(group.id, e)}
                        />
                        Upload
                      </label>
                      {group.fileBlob && group.fileName && (
                        <button
                          className="btn btn-sm btn-outline-primary me-2"
                          onClick={() => downloadFile(group.id)}
                        >
                          Download
                        </button>
                      )}
                      <Link
                        className="btn btn-sm btn-outline-primary me-2"
                        to={`/AddTask/${group.id}`}
                      >
                        +
                      </Link>
                      <Link
                        className="btn btn-sm btn-outline-secondary me-2"
                        to={`/EditGroup/${group.id}`}
                      >
                        Edit
                      </Link>
                      <button
                        className="btn btn-sm btn-outline-danger"
                        onClick={() => deleteGroup(group.id)}
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            );
          })
        ) : (
          <div className="col-12 text-center">
            <p className="text-muted">No matching groups found.</p>
          </div>
        )}
      </div>
    </div>
  );
}
