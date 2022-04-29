import React from "react";

import "./Home.css";
import winston1 from "./pictures/Winston.jpg";
import winnie2 from "./pictures/winnie2.jpg";
import winnie3 from "./pictures/winnie3.jpg";
import winnie4 from "./pictures/winnie4.jpg"

function Home() {
    return (
        <div className="main">
            <h3>Andrew Soby's Graduate Project: W.I.N.S.T.O.N Project</h3>

            <br/>
            <div className="area"><h5 className="text">Brief Project Description</h5>
                <p>This is my Graduate Project that explores building an efficient IoT pipeline. This project is made up of 3 main components.</p>
                <p>1. Python Simulated Device - This device uses real iot data to be able to burst send thousands of messages to the pipeline.</p>
                <p>2. Infrastructure - This is the structure of the pipeline and it includes an MQTT Broker, Kafka Message Queue, Postgresql, and MongoDB.</p>
                <p>3. Microservices - Most of these microservices are not user facing but are necessary to enable an event driven architecture.</p>
                <p>I Have deliberated carefully on a project name and have settled on W.I.N.S.T.O.N after my dog named Winston. It stands for When IoT Needs Systematic Tuning On Nuances or W.I.N.S.T.O.N for short.</p>
            </div>
            <br/>
            <br/>
            <div className="area"> <h5>Query Service</h5>
                 <p>The Query service is designed to allow users to easily view the devices that are stored after the system ingests them.</p>
            </div>
            <br/>
            <br/>
            <div className="area"><h5>Alarm Service</h5>
                <p>The Alarm service receives and stores alarms and then displays the top ten most recent alarms if there are ten.</p>

            </div>
            <br/>
            <br/>
            <div className="area"><h5>Export Service</h5>
                <p>The Export service is designed to allow users to evaluate device data easily into a csv format so that other means of data analysis can be performed on it.</p>
            </div>

            <br/>
            <br/>
            <div className="area"><h5>Dog Section</h5>
                <p>I have decided to include a dog section into the home page to make the front end experience even more enjoyable as originally I had not planned for a front end.</p>
                <p>These photos are of my dog Winston who is two and a half years old.</p>
                <p>The first photo is also the same as the icon of my project. It is a picture of Winston and Me.</p>
                <img src={winston1} alt="Winston and Andrew"/>
                <br/>
                <br/>
                <p>This is a picture of Winston enjoying Halloween!</p>
                <img src={winnie2} alt="Halloween Winnie"/>
                <br/>
                <br/>
                <p> This is a picture of Winston snuggled in after a hard day of play</p>
                <img src={winnie3} alt="Snuggled Winnie"/>
                <br/>
                <br/>
                <p> This is an image of Winston sitting on a table because he wanted to!</p>
                <br/>
                <img src={winnie4} alt="Table Winnie"/>
                <br/>
                <p> </p>
                <br/>
            </div>
            <p> </p>
            <br/>
        </div>
    );
}

export default Home;