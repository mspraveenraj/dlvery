import React from 'react';
import { toast } from 'react-toastify';
import HomePageDataService from '../HomePageDataService';
import LoadingIndicator from './LoadingIndicator';

class ChangePassword extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {
                password: '',
            },
           confirmPassword: '',
           notMatch: false,
           loading: false
        }
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {

        this.props.currentUser!== undefined && this.setState({user: {...this.props.currentUser}, loading: false})
        
    }

    handleChange = (event) => {
        this.setState( {...this.state, user: {...this.state.user, [event.target.name]: event.target.value} } );
    }

    handleConfirmChange = (event) => {
        this.setState({...this.state, [event.target.name]: event.target.value});
    }

    handleSubmit = (event) => {
        event.preventDefault();
        
        if(this.state.user.password === this.state.confirmPassword) {
            this.setState({...this.state, notMatch: false, loading: true});
            this.state.user.password!== '' && HomePageDataService.updateCurrentUserPassword(this.props.currentUser.id, this.state.user) 
                .then(this.setState({...this.state, notMatch: false, loading: false}))
                .then(toast.success("Password Updated!", {autoClose: 3000})); 
        }
        else {
            this.setState({...this.state, notMatch: true, loading: false })
        }
    }
    render() {
      require("./ChangePassword.css");
    return (
    <>
    {this.state.loading && <LoadingIndicator/>}
    
    <div id="password-box">
        <div className="left">
        <h1>Change Password</h1>
        
        <form onSubmit={this.handleSubmit}>
        
          <input type="password" name="password" onChange={this.handleChange} placeholder="New Password" autocomplete="new-password" required/>
        
          <input type="password" name="confirmPassword" onChange={this.handleConfirmChange} placeholder="Re-Enter Password" autocomplete="new-password" required />
          {this.state.notMatch && <h3 style={{margin: "0 0 20px 0", fontWeight: "300", fontSize: "18px", color: "rgb(255, 0, 0)" } } >Password Doesn't Match</h3>}

          <button type="submit">Submit</button>

         </form>
        </div>
    </div>
   </>
    )
  }
}

export default ChangePassword;