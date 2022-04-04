import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import {Nav, Navbar} from "react-bootstrap";
import Alarm from "./pages/Alarm";
import Query from "./pages/Query";
import Export from "./pages/Export";
import Home from "./pages/Home";

import {
    BrowserRouter as Router,
    NavLink,
    Route,
    Routes
} from "react-router-dom";

function App() {
    return (
        <div className="backColor">
        <Router>
            <div className="backColor">
                <Navbar bg="dark" variant="dark" sticky="top" >
                    <Navbar.Brand className="gradbrand">
                        Graduate Project
                    </Navbar.Brand>

                    <Navbar.Toggle/>
                    <Navbar.Collapse>
                        <Nav className="test">
                            <Nav.Link as={NavLink} to="/" exact className="test">Home</Nav.Link>
                            <Nav.Link as={NavLink} to="/query" className="test">Query</Nav.Link>
                            <Nav.Link as={NavLink} to="/alarm"className="test" >Alarm</Nav.Link>
                            <Nav.Link as={NavLink} to="/exports"className="test" >Export</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                <Routes>
                    <Route path="/" exact element={<Home/>}/>
                    <Route path="/query" exact element={<Query/>}/>
                    <Route path="/alarm" exact element={<Alarm/>}/>
                    <Route path="/exports" exact element={<Export/>}/>
                </Routes>
            </div>
        </Router>
</div>
    );
}

export default App;
