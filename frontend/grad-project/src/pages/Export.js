import React from "react";
import {CSVLink} from "react-csv";
import './Export.css';

class Export extends React.Component {
    csvLink = React.createRef()
    state = {data: [], deviceList: "", devices: []}

    fetchData = async () => {
        if (this.state.deviceList === "") {
            console.log("device list is empty")
        } else if (!(this.state.deviceList.includes(","))) {
            console.log("doesnt include multiple devices")
            let arr = []
            arr.push(this.state.deviceList)
            console.log(arr)
            await this.setState({devices: arr})
            console.log(this.state.devices)
        } else {
            await this.setState({devices: this.state.deviceList.split(",")})
            console.log(this.state.devices)
            console.log(this.state.deviceList.split(","))
        }

        console.log(this.state.devices)
        console.log(this.state.devices.toString())
        if (this.state.deviceList !== "") {
            console.log("string is not empty")
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({devices: this.state.devices})
            };
            console.log(requestOptions.body.toString())
            fetch('http://localhost:8080/export/getDevices', requestOptions)
                .then(response => response.json())
                .then(data => {
                    this.setState({data}, () => {
                        console.log(data)
                        // click the CSVLink component to trigger the CSV download
                        this.csvLink.current.link.click()
                    })
                })
        } else {
            fetch('http://localhost:8080/export/getAllDevices')
                .then(response => response.json())
                .then(data => {
                    this.setState({data}, () => {
                        console.log(data)
                        console.log("using access endpoint")
                        // click the CSVLink component to trigger the CSV download
                        this.csvLink.current.link.click()
                    })
                })
        }
    }


    render() {
        return (
            <div className="main">
                <h2>Export Service</h2>
                <br/>
                <div className="area">
                    <h5>Enter device id's in the input box below for export separated by "," ex. 1,2,3 </h5>
                    <h5>If no devices are entered, then an export of all devices will be downloaded.</h5>
                </div>
                <br/>
                <br/>
                <form onSubmit={this.handleSubmit}>
                    <input type="text" value={this.state.deviceList}
                           onInput={e => this.setState({deviceList: e.target.value})}/>
                </form>
                <br/>
                <button onClick={this.fetchData}>Download CSV</button>

                <CSVLink
                    data={this.state.data}
                    filename="data.csv"
                    className="hidden"
                    ref={this.csvLink}
                    target="_blank"
                />
            </div>
        );
    }
}

export default Export;