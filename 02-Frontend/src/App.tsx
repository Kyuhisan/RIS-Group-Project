import "./styles/styles.css";
import Home from "./pages/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AddTask from "./tasks/AddTask";
import EditTask from "./tasks/EditTask";
import AddGroup from "./groups/AddGroup";
import EditGroup from "./groups/EditGroup";
import RecurringGroups from "./pages/RecurringGroups";

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/AddTask" element={<AddTask />} />
          <Route path="/AddGroup" element={<AddGroup />} />
          <Route path="/EditTask/:id" element={<EditTask />} />
          <Route path="/AddTask/:groupId" element={<AddTask />} />
          <Route path="/EditGroup/:id" element={<EditGroup />} />
          <Route path="/RecurringGroups" element={<RecurringGroups />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
