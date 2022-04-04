import React, {useState, useEffect} from "react";
import './Alarm.css';

function Alarm() {
    const [alarms, setAlarms] = useState([])
    let count = 1;
    useEffect(() => {
        fetch('http://localhost:8083/getAlarms')
            .then(response => response.json())
            .then(data => setAlarms(data));
    }, [])

    console.log(alarms)
    return (
        <div className="main">
            <h2>Alarm Service</h2>
            <br/>
            <div className="area">
                <h5> Recent Alarms</h5>
                {alarms.map(s => (
                    <p className="alarms" key={s.id}>Alarm {count++}: {'\u007B'} "uuid": "{s.id}","deviceId":
                        "{s.deviceId}", "timestamp": "{s.timestamp}", "temperature":
                        "{s.temperature}", "humidity": "{s.humidity}", "Severity": "{s.severity}"}</p>
                ))}

            </div>

        </div>
    );
}

export default Alarm;