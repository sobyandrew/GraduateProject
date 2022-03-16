import React, {useState, useEffect} from "react";

function countClick() {
    console.log("click occurred");
}

function Exports() {
    const [devices, setDevices] = useState([])

    const handleClick = async () => {
        const resp = await fetch('http://localhost:8082/getDevice/1')
            .then(response => response.json())
            .then(data => setDevices(data));

        console.log("after fetch");
    }
    console.log(devices);
    return (
        <div>
            Exports home page
            <button onClick={handleClick}> click</button>
            <div>{devices.map(s => (
                <p>{'\u007B'} "uuid": "{s.id}","deviceId": "{s.deviceId}", "timestamp": "{s.timestamp}", "temperature":
                    "{s.temperature}", "humidity": "{s.humidity}"}</p>))}</div>
        </div>
    );
}

export default Exports;