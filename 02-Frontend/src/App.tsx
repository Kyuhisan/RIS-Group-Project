import "./styles/styles.css";
import Home from "./pages/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AddTask from "./tasks/addTask";
import EditTask from "./tasks/EditTask";

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/AddTask" element={<AddTask />} />
          <Route path="/EditTask/:id" element={<EditTask />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
