import React from 'react';

class Profile extends React.Component {

    render() {
        require("./Profile.css")
        return (
            <div stye={{marginTop: "60px"}} >
            <div className="profile-container">

                    <div className="profile-info">
                        <div className="profile-avatar">
                            { 
                                this.props.currentUser.imageUrl ? (
                                    <img src={this.props.currentUser.imageUrl} alt={this.props.currentUser.name}/>
                                ) : (
                                    <div className="text-avatar">
                                        <span>{this.props.currentUser.firstName }</span>
                                    </div>
                                )
                            }
                        </div>
                        <br/>
                        <div className="profile-name">
                           <h2>{this.props.currentUser.fisrtName}</h2>
                           <p className="profile-email">{this.props.currentUser.email}</p>
                        </div>
                    </div>
                </div>
                </div>
        );
    }
}

export default Profile;