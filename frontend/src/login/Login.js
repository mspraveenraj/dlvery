import React, { Component } from 'react';
import { GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL, ACCESS_TOKEN } from '../constants';
import { login } from '../util/APIUtils';
import { Redirect } from 'react-router-dom'
import 'react-toastify/dist/ReactToastify.css';
import { toast } from 'react-toastify';
import dlvery_logo from '../img/dlvery_logo.png'

class Login extends Component {
    componentDidMount() {
        // If the OAuth2 login encounters an error, the user is redirected to the /login page with an error
        // Here we display the error and then remove the error query parameter from the location.
        if(this.props.location.state && this.props.location.state.error) {
            //setTimeout(() => {
                //Alert.error(this.props.location.state.error, {
                 //   timeout: 5000
                //});
                
                toast.error(this.props.location.state.error, {autoClose: 5000});
                setInterval( () =>{
                this.props.history.replace({
                    pathname: this.props.location.pathname,
                    state: {}
                });
            }, 5000);
        }
    }
    
    render() { 
        require('./Login.css')
        if(this.props.authenticated) {

            return <Redirect
                to={{
                pathname: "/",
                state: { from: this.props.location }
            }}/>;            
        }

        return (
            <div className="generallogin-box">
            <div className="generallb-header">
               
                <img id="login-box-link" src={dlvery_logo} width="50px" height="50px" alt={"dlveryLogo"} />
            </div>
            <br/><br/>
            <h2 style={{textAlign: "center", fontSize: "20px"}}>DLTeam Login</h2>
            <br/><br/>
                <SocialLogin />
            <h2 style={{textAlign: "center", fontSize: "20px", paddingTop:"100px"}}>InvTeam Login</h2>
                <LoginForm {...this.props} />
            </div>
        );
    }
}

class SocialLogin extends Component {
    render() {
        return (
            <div className="social-login">
            <a href={GOOGLE_AUTH_URL}>
                <i className="fa fa-google fa-lg"></i>
                Login with Google
            </a>
            <a href={FACEBOOK_AUTH_URL}>
            <i className="fa fa-facebook fa-lg"></i>
                 Login with Facebook
            </a>
            </div>
        );
    }
}


class LoginForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: ''
        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;        
        const inputValue = target.value;

        this.setState({
            [inputName] : inputValue
        });        
    }

    handleSubmit(event) {
        event.preventDefault();   

        const loginRequest = Object.assign({}, this.state);

        login(loginRequest)
        .then(response => {
            localStorage.setItem(ACCESS_TOKEN, response.accessToken);
            //Alert.success("You're successfully logged in!");
            this.props.history.push("/");
        }).catch(error => {
            //Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
            toast.error(error.message, {autoClose: 5000});
                setInterval( () =>{
                
            }, 5000);
        });
    }
    
    render() {
        return (  
            
            <form className="generalusername-login" onSubmit={this.handleSubmit}>
                <div className="generalu-form-group">
                    <input type="text" name="username" placeholder="Username" value={this.state.username} onChange={this.handleInputChange} autocomplete="username" required/>
                </div>
                <div className="generalu-form-group">
                     <input type="password" name="password" placeholder="Password"  value={this.state.password} onChange={this.handleInputChange} autocomplete="current-password" required/>
                </div>
                <div className="generalu-form-group">
                    <button type="submit">Log in</button>
                </div>
                <div className="admin-btn">
                    <button onClick={()=>this.props.history.push("/adminPage/login")} >Admin?</button>
                </div>
            </form>
        );
    }
}

export default Login;