import React, { Component } from 'react';
import { toast } from 'react-toastify';
import '../../common/reset.css';
import {ACCESS_TOKEN } from '../../constants';
import { login } from '../../util/APIUtils';

class AdminLogin extends Component {
    
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: ''
        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange = (event) =>  {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName] : inputValue
        });        
    }

    handleSubmit = (event) => {
        event.preventDefault();   

        const loginRequest = Object.assign({}, this.state);

        login(loginRequest)
        .then(response => {
            localStorage.setItem(ACCESS_TOKEN, response.accessToken);
            this.props.history.push("/");
        }).catch(error => {
           
            toast.error("Oops! Something went wrong. Please try again!", {autoClose: 3000});
        });
    }

    componentDidMount() {
        localStorage.removeItem(ACCESS_TOKEN);
    }
    
    render() {
   
    require("./AdminLogin.css")
    return(
        
    <div className="login-page">
    <div className="form">
      <div className="login">
        <div className="login-header">
          <br/>
          <h3 style={{fontWeight: 600, textAlign: 'center'}}>LOGIN</h3>
          <br/>
          <p>Admin Login to Dlvery App</p>
        </div>
      </div>
      <form className="login-form" onSubmit={this.handleSubmit}>
        <input type="text"  name="username" placeholder="username" onChange={this.handleInputChange} value={this.state.username} autoFocus autoComplete="username" required/>
        <br/>
        <input type="password" name="password" placeholder="password" onChange={this.handleInputChange} value={this.state.password} autoComplete="current-password" required/>
        <button type="submit">login</button>
      </form>
    </div>
  </div>
)
    }
}

export default AdminLogin;