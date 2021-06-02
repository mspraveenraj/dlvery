import React, { Component } from 'react';
import {
  Route,
  Switch
} from 'react-router-dom';
import Home from './home/Home';
import Login from './login/Login';
import Signup from './signup/Signup';
import Profile from './profile/Profile';
import OAuth2RedirectHandler from './oauth2/OAuth2RedirectHandler';
import NotFound from './common/NotFound';
import LoadingIndicator from './common/LoadingIndicator';
import { getCurrentUser } from './util/APIUtils';
import { ACCESS_TOKEN } from './constants';
import PrivateRoute from './common/PrivateRoute';
import InvTeamPage from './Components/InvTeam/InvTeamPage';
import UpdateInventoryForm from './Components/InvTeam/UpdateInventoryForm';
import AddInventoryForm from './Components/InvTeam/AddInventoryForm';
import DLTeamPage from './Components/DLTeam/DLTeamPage';
import DLTeamDeliveryPriority from './Components/DLTeam/DLTeamDeliveryPriority';
import DLTeamDeliveryPending from './Components/DLTeam/DLTeamDeliveryPending';
import DLTeamDeliveryAll from './Components/DLTeam/DLTeamDeliveryAll';
import DLTeamUpdateInventory from './Components/DLTeam/DLTeamUpdateInventory';
import AdminPage from './Components/Admin/AdminPage';
import Unauthorized from './common/Unauthorized';
import history from './history';
import AdminLogin from './Components/Admin/AdminLogin';
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ChangeAddress from './common/ChangeAddress';
import ChangePassword from './common/ChangePassword';
import AppHeader1 from './common/AppHeader1';
import Logout from './common/Logout';

class App1 extends Component {
  constructor(props) {
    super(props);
    this.state = {
      authenticated: false,
      currentUser: null,
      loading: true
    }

    this.loadCurrentlyLoggedInUser = this.loadCurrentlyLoggedInUser.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
  }

  loadCurrentlyLoggedInUser() {
    this.setState({
      loading: true
    });

    getCurrentUser()
    .then(response => {
      this.setState({
        currentUser: response,
        authenticated: true,
        loading: false
      });
    }).catch(error => {
      this.setState({
        loading: false
      });  
    });    
  }

  handleLogout() {
    localStorage.removeItem(ACCESS_TOKEN);
    this.setState({
      authenticated: false,
      currentUser: null
    });

    toast.success("You're safely logged out!", {autoClose: 3000});
    
    setTimeout(() => {
      history.push("/login");
    }, 5000);
    
  }

  componentDidMount() {
    this.loadCurrentlyLoggedInUser();
  }

  render() {
    if(this.state.loading) {
      return <LoadingIndicator />
    }

    return (
      <div >
        <div >
          { this.state.currentUser!==null && this.state.currentUser.team.teamName!== "Admin" 
              && <PrivateRoute component={AppHeader1} roles={["Admin", "InvTeam", "DLTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser} />
          }
        </div>
        <div >
          <Switch>
       
            <PrivateRoute exact path="/" component={Home}  roles={["Admin", "InvTeam", "DLTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser}></PrivateRoute>           
            <PrivateRoute exact path="/profile"  roles={["Admin", "InvTeam", "DLTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser}
              component={Profile}></PrivateRoute>

            <PrivateRoute exact path="/profile/changeAddress" roles={["Admin", "InvTeam", "DLTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser}
              component={ChangeAddress}></PrivateRoute>
              
            <PrivateRoute exact path="/profile/changePassword" roles={["Admin", "InvTeam", "DLTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser}
              component={ChangePassword}></PrivateRoute>

            <Route exact path="/login"
              render={(props) => <Login authenticated={this.state.authenticated} {...props} />}></Route>

            <Route exact path="/logout" component={Logout}  authenticated={this.state.authenticated}/>

            <Route exact path="/signup"
              render={(props) => <Signup authenticated={this.state.authenticated} {...props} />}></Route>
            <Route exact path="/oauth2/redirect" component={OAuth2RedirectHandler}></Route>  
            
            <PrivateRoute exact path="/invTeamPage" component={InvTeamPage} roles={["InvTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>
            <PrivateRoute exact path="/invTeamPage/updateInventoryForm/:id" component={UpdateInventoryForm} roles={["InvTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>
            <PrivateRoute exact path="/invTeamPage/addInventoryForm" component={AddInventoryForm} roles={["InvTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>
            <PrivateRoute exact path="/dlTeamPage" component={DLTeamPage} roles={["DLTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser} />
            <PrivateRoute exact path="/dlTeamPage/deliveryPriority" component={DLTeamDeliveryPriority} roles={["DLTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>
            <PrivateRoute exact path="/dlTeamPage/deliveryPending" component={DLTeamDeliveryPending} roles={["DLTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>
            <PrivateRoute exact path="/dlTeamPage/deliveryAll" component={DLTeamDeliveryAll} roles={["DLTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser} />
            
            <PrivateRoute exact path="/dlTeamPage/inventory/:id" component={DLTeamUpdateInventory} roles={["DLTeam"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>
         
            <Route exact path="/adminPage/login"
                    render={(props) => <AdminLogin authenticated={this.state.authenticated} {...props} />}></Route>
            <PrivateRoute exact path="/adminPage" component={AdminPage} roles={["Admin"]} authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>

            <Route exact
                 path="/praveenrajms"
                render={ () => window.location = "https://bit.ly/praveenrajms" }
              />
            
            <Route exact path="/unauthorized">
               <Unauthorized /> </Route>
            <Route component={NotFound}></Route>
           
          </Switch>
          <ToastContainer/>
        </div>
        
      </div>
    );
  }
}

export default App1;