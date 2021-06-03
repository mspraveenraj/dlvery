import React, { Component } from 'react';
import { GOOGLE_AUTH_URL, ACCESS_TOKEN } from '../constants';
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
        
            <h2 style={{textAlign: "center", fontSize: "20px"}}>DLTeam Login</h2>
            <br/>
                <SocialLogin />
            <h2 style={{textAlign: "center", fontSize: "20px", paddingTop:"80px"}}>InvTeam Login</h2>
                <LoginForm {...this.props} />
            <div>
                <p style={{textAlign: "right", paddingTop: "90%"}}>Built by, <a className="myPortfolio" href="/praveenrajms" >PraveenRajMS</a> </p>
            </div>
            </div>
        );
    }
}

//For FB put this below in Google
//<a href={FACEBOOK_AUTH_URL}>
            //<i className="fa fa-facebook fa-lg"></i>
            //     Login with Facebook
           // </a>
//Below is CSS
//.social-login{
  //  position:relative;
  //  float: left;
  //  width: 100%;
  //  height:auto;
   // padding: 10px 0 15px 0;
   // border-bottom: 1px solid #eee;
  //}
           //.social-login a{
           // position:relative;
           // float: left;
           // width:calc(40% - 8px);
           // text-decoration: none;
           // color: #fff;
           // border: 1px solid rgba(0,0,0,0.05);
           // padding: 12px;
           // border-radius: 2px;
           // font-size: 12px;
           // text-transform: uppercase;
           // margin: 0 3%;
           // text-align:center;
         // }
         // .social-login a i{
          //  position: relative;
          //  float: left;
          //  width: 20px;
          //  top: 2px;
         // }

//.social-login a:last-child{
 //  background-color: #49639F ;
  // }
class SocialLogin extends Component {
    render() {
        return (
            <div className="social-login">
            <a href={GOOGLE_AUTH_URL}>
                <i className="fa fa-google fa-lg"></i>
                Login with Google
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
            this.props.history.push("/");
        }).catch(error => {
            
            if(error.message === "Unexpected end of JSON input")
            {
                toast.error("Bad Credentials", {autoClose: 5000});
                setInterval( () =>{
                
            }, 5000);
            }
            else {
                toast.error(error.message, {autoClose: 5000});
                    setInterval( () =>{
                        }, 5000);
            }
        });
    }
    
    render() {
        return (  
            
            <form className="generalusername-login" onSubmit={this.handleSubmit}>
                <div className="generalu-form-group">
                    <input type="text" name="username" placeholder="Username" value={this.state.username} onChange={this.handleInputChange} autoFocus autoComplete="username" required/>
                </div>
                <div className="generalu-form-group">
                     <input type="password" name="password" placeholder="Password"  value={this.state.password} onChange={this.handleInputChange} autoComplete="current-password" required/>
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