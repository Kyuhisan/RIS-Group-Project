import React, { useEffect, useState } from "react";
import axios from "axios";
import "../../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Link } from "react-router-dom";

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
  fileBlob?: string | null; // Base64 string
  fileName?: string | null; // File name
  creationDate: string; // ISO format date
  period?: Period | null; // Periodicity (optional)
}

export default function RecurringGroups() {
  const [groups, setTaskGroups] = useState<TaskGroup[]>([]);

  useEffect(() => {
    LoadGroups();
  }, []);

  const LoadGroups = async () => {
    try {
      const result = await axios.get("http://localhost:8888/api/groups");
      const recurringGroups = result.data.filter(
        (group: TaskGroup) => group.period !== null
      );
      setTaskGroups(recurringGroups);
    } catch (error) {
      console.error("Error loading recurring groups:", error);
    }
  };

  return (
    <div className="container mt-4">
      <div className="row mb-4">
        <div className="col-md-12 text-md-start">
          <Link className="btn btn-secondary mb-3" to="/">
            Back to Home
          </Link>
        </div>
      </div>
      <div className="row">
        {groups.length > 0 ? (
          groups.map((group) => {
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
                    {group.period && (
                      <p className="text-muted">
                        Recurring Period: {group.period}
                      </p>
                    )}
                  </div>
                </div>
              </div>
            );
          })
        ) : (
          <div className="col-12 text-center">
            <p className="text-muted">No recurring task groups found.</p>
          </div>
        )}
      </div>
    </div>
  );
}
