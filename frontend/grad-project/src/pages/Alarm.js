import React, {useState, useEffect} from "react";
import './alarm.css';

function Alarm() {
    const [alarms, setAlarms] = useState([])

    useEffect(() => {
        fetch('http://localhost:8083/getAlarms')
            .then(response => response.json())
            .then(data => setAlarms(data));
    }, [])

    console.log(alarms)
    return (
        <div className="alarm">
            <h2>Alarm</h2>
            <br/>
            <p>This is the alarms page and will show the 10 most recent alarms</p>
            <div>{alarms.map(s => (
                <p key={s.id}>{'\u007B'} "uuid": "{s.id}","deviceId": "{s.deviceId}", "timestamp": "{s.timestamp}", "temperature":
                    "{s.temperature}", "humidity": "{s.humidity}", "Severity": "{s.severity}"}</p>))}
            </div>

        </div>
    );
}

export default Alarm;