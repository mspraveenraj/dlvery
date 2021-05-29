import React, { Component } from 'react';

class Home extends Component {
    
    render() {
        require("./Home.css");
        setTimeout( () => {
        this.props.currentUser!== null 
                && this.props.currentUser.team.teamName === 'Admin' && this.props.history.push("/adminPage")

        this.props.currentUser!== null 
                && this.props.currentUser.team.teamName === 'InvTeam' && this.props.history.push("/invTeamPage")

        this.props.currentUser!== null 
                && this.props.currentUser.team.teamName === 'DLTeam' && this.props.history.push("/dlTeamPage")
        }, 3000 );
        return (
            <div className="home-container">
                    <div className="graf-bg-container">
                        <div className="graf-layout">
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                        </div>
                    </div>
                    <h1 className="home-title">Welcome to Dlvery App</h1>
                    <h3 className="home-title">Let's get to Work...</h3>
                </div>
        )
    }
}

export default Home;