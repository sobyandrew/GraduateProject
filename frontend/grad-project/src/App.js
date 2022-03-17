import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import {Nav, Navbar} from "react-bootstrap";
import Alarm from "./pages/Alarm";
import Query from "./pages/Query";
import Exports from "./pages/Exports";
import Export2 from "./pages/Export2";
import Home from "./pages/Home";

import {
    BrowserRouter as Router,
    Link,
    NavLink,
    Route,
    Routes
} from "react-router-dom";

function App() {
    return (
        <Router>
            <div>
                <Navbar bg="dark" variant="dark" sticky="top" >
                    <Navbar.Brand className="gradbrand">
                        Graduate Project
                    </Navbar.Brand>

                    <Navbar.Toggle/>
                    <Navbar.Collapse>
                        <Nav className="test">
                            <Nav.Link as={NavLink} to="/" exact className="test">Home</Nav.Link>
                            <Nav.Link as={NavLink} to="/exports" className="test">Export</Nav.Link>
                            <Nav.Link as={NavLink} to="/query" className="test">Query</Nav.Link>
                            <Nav.Link as={NavLink} to="/alarm"className="test" >Alarm</Nav.Link>
                            <Nav.Link as={NavLink} to="/export2"className="test" >Export2</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                <Routes>
                    <Route path="/" exact element={<Home/>}/>
                    <Route path="/exports" exact element={<Exports/>}/>
                    <Route path="/query" exact element={<Query/>}/>
                    <Route path="/alarm" exact element={<Alarm/>}/>
                    <Route path="/export2" exact element={<Export2/>}/>
                </Routes>
            </div>
        </Router>
    );
}

export default App;
