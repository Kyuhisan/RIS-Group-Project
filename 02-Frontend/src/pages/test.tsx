import React, { useState } from "react";
import axios from "axios";

// Define types for Task and TaskGroup
type Task = {
  name: string;
  state: "Unfinished" | "WIP" | "Done";
};

type TaskGroupType = {
  name: string;
  tasks: Task[];
};

export default function Home() {
  const [groups, setGroups] = useState<TaskGroupType[]>([]);
  const [groupName, setGroupName] = useState<string>("");

  // Function to add a new Task Group
  const addTaskGroup = (): void => {
    if (!groupName.trim()) {
      alert("Please enter a task group name!");
      return;
    }
    setGroups([...groups, { name: groupName, tasks: [] }]);
    setGroupName("");
  };

  // Function to delete a Task Group
  const deleteTaskGroup = (index: number): void => {
    const newGroups = groups.filter((_, i) => i !== index);
    setGroups(newGroups);
  };

  return (
    <div className="container">
      <h1>Advanced TO-DO List</h1>

      {/* Add Task Group Section */}
      <div className="add-group">
        <input
          type="text"
          value={groupName}
          onChange={(e) => setGroupName(e.target.value)}
          placeholder="Enter new task group name"
        />
        <button onClick={addTaskGroup}>Add Group</button>
      </div>

      {/* Task Groups Container */}
      <div id="groupsContainer">
        {groups.map((group, index) => (
          <TaskGroup
            key={index}
            group={group}
            deleteGroup={() => deleteTaskGroup(index)}
          />
        ))}
      </div>
    </div>
  );
};

// Define prop types for TaskGroup
type TaskGroupProps = {
  group: TaskGroupType;
  deleteGroup: () => void;
};

// TaskGroup Component
const TaskGroup: React.FC<TaskGroupProps> = ({ group, deleteGroup }) => {
  const [tasks, setTasks] = useState<Task[]>(group.tasks);
  const [taskName, setTaskName] = useState<string>("");

  // Add Task to Task Group
  const addTask = (): void => {
    if (!taskName.trim()) {
      alert("Please enter a task name!");
      return;
    }
    setTasks([...tasks, { name: taskName, state: "Unfinished" }]);
    setTaskName("");
  };

  // Delete Task
  const deleteTask = (taskIndex: number): void => {
    const newTasks = tasks.filter((_, i) => i !== taskIndex);
    setTasks(newTasks);
  };

  // Update Task State
  const updateTaskState = (taskIndex: number, state: Task["state"]): void => {
    const updatedTasks = tasks.map((task, i) =>
      i === taskIndex ? { ...task, state } : task
    );
    setTasks(updatedTasks);
  };

  // Progress calculation
  const completedTasks = tasks.filter((task) => task.state === "Done").length;
  const progressPercentage =
    tasks.length > 0 ? (completedTasks / tasks.length) * 100 : 0;

  return (
    <div className="task-group">
      <div className="group-header">
        <h2>{group.name}</h2>
        <button className="delete-group" onClick={deleteGroup}>
          Delete Group
        </button>
      </div>

      {/* Progress Bar */}
      <div className="progress-container">
        <div
          className="progress-bar"
          style={{
            width: `${progressPercentage}%`,
            backgroundColor:
              progressPercentage === 100
                ? "#28a745"
                : progressPercentage > 0
                ? "#ffc107"
                : "#dc3545",
          }}
        />
      </div>

      {/* Add Task Section */}
      <div className="add-task">
        <input
          type="text"
          value={taskName}
          onChange={(e) => setTaskName(e.target.value)}
          placeholder="Enter new task name"
        />
        <button onClick={addTask}>Add Task</button>
      </div>

      {/* Task List */}
      <ul className="task-list">
        {tasks.map((task, index) => (
          <TaskItem
            key={index}
            task={task}
            deleteTask={() => deleteTask(index)}
            updateTaskState={(state) => updateTaskState(index, state)}
          />
        ))}
      </ul>
    </div>
  );
};

// Define prop types for TaskItem
type TaskItemProps = {
  task: Task;
  deleteTask: () => void;
  updateTaskState: (state: Task["state"]) => void;
};

// TaskItem Component
const TaskItem: React.FC<TaskItemProps> = ({
  task,
  deleteTask,
  updateTaskState,
}) => {
  return (
    <li className="task-item">
      <input type="checkbox" disabled style={{ marginRight: "10px" }} />

      <span className={`task-name state-${task.state}`}>{task.name}</span>

      {/* State Selector */}
      <div className="task-state">
        <select
          value={task.state}
          onChange={(e) => updateTaskState(e.target.value as Task["state"])}
        >
          <option value="Unfinished">Unfinished</option>
          <option value="WIP">WIP</option>
          <option value="Done">Done</option>
        </select>
      </div>

      <button className="delete-task" onClick={deleteTask}>
        Delete
      </button>
    </li>
  );
};