import React, {useState, useEffect} from "react";

function Query() {
    const [query, setQuery] = useState(" ")
    const [devices, setDevices] = useState([])
    const handleChange = (event) => {

        setQuery(event.target.value);
    }
    const handleSubmit = async (event) => {
        // console.log(event);
        event.preventDefault();
        console.log(query);
        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({query: query.toString()})
        };
        const resp = await fetch('http://localhost:8081/query', requestOptions)
            .then(response => response.json())
            .then(data => setDevices(data.queryResults));

        console.log("after fetch");
    }

    console.log(devices);
    return (
        <div>
            Query home page

            <form onSubmit={handleSubmit}>
                <input type="text" value={query} onInput={e => setQuery(e.target.value)}/>
                <input type="submit" value="Submit Query" onClick={handleSubmit}/>
            </form>
            <div>{devices.map(s => (
                <p>{'\u007B'} "uuid": "{s.id}","deviceId": "{s.deviceid}", "timestamp": "{s.time}", "temperature":
                    "{s.temperature}", "humidity": "{s.humidity}"}</p>))}
            </div>
        </div>
    );
}

export default Query;