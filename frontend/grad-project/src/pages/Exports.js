import React, {useState, useEffect} from "react";

import {CSVLink} from "react-csv";


//http://localhost:8082/getAllDevices


function Exports() {
    const [devices, setDevices] = useState([])

    const handleClick = async () => {
        const resp = await fetch('http://localhost:8082/getAllDevices')
           // .then(data => setDevices(data))
            .then(response => response.json())
            .then(data => setDevices(data));

        console.log("after fetch");
    }
    console.log(devices);
    return (
        <div>
            Exports home page
            <button onClick={handleClick}> Generate Export</button>
            <button>
                <CSVLink data={devices} filename="devices.csv" > Download CSV File</CSVLink>
            </button>
            {/*<CSVLink data={devices} filename="devices.csv"> Download CSV File</CSVLink>*/}
            {/*<div>{devices.map(s => (*/}
            {/*    <p>{'\u007B'} "uuid": "{s.id}","deviceId": "{s.deviceId}", "timestamp": "{s.timestamp}", "temperature":*/}
            {/*        "{s.temperature}", "humidity": "{s.humidity}"}</p>))}</div>*/}
        </div>
    );
}

export default Exports;
//
// class MyCsvLink extends React.Component {
//     csvLink = React.createRef()
//     state = { data: [] }
//
//     fetchData = () => {
//         fetch('/mydata').then(data => {
//             this.setState({ data }, () => {
//                 // click the CSVLink component to trigger the CSV download
//                 this.csvLink.current.link.click()
//             })
//         })
//     }
//
//     render() {
//         return (
//             <div>
//                 <button onClick={this.fetchData}>Download CSV</button>
//
//                 <CSVLink
//                     data={this.state.data}
//                     filename="data.csv"
//                     className="hidden"
//                     ref={this.csvLink}
//                     target="_blank"
//                 />
//             </div>
//         )
//     }
// }