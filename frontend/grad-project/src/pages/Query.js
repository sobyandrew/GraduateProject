import React, {useState} from "react";
import "./Query.css";

function Query() {
    const [query, setQuery] = useState(" ")
    const [devices, setDevices] = useState([])
    let row = 1;

    const handleSubmit = async (event) => {
        row = 1;
        event.preventDefault();
        console.log(query);
        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({query: query.toString()})
        };
        await fetch('http://localhost:8080/query', requestOptions)
            .then(response => response.json())
            .then(data => setDevices(data.queryResults));

        console.log("after fetch");
    }

    console.log(devices);
    return (
        <div className="main">
            <h2>Query Service</h2>
            <br/>
            <div className="area">
                <h5>Submit a Query to receive results ex. Select * from deviceInfo;</h5>
            </div>
            <br/>

            <br/>
            <form onSubmit={handleSubmit}>
                <input size="75" type="text" value={query} onInput={e => setQuery(e.target.value)}/>
                <br/>
                <br/>
                <input type="submit" value="Submit Query" onClick={handleSubmit}/>
            </form>
            <br/>
            <div className="area">
                <h5>Query Results</h5>{devices.map(s => (
                <div>
                    Row {row++}:

                    <p className="devices"> {'\u007B'} "uuid": "{s.id}","deviceId": "{s.deviceid}", "timestamp":
                        "{s.time}", "temperature":
                        "{s.temperature}", "humidity": "{s.humidity}"} </p>
                    <br/>
                </div>))}
            </div>
        </div>
    );
}

export default Query;