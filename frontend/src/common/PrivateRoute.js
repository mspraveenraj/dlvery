import React from 'react';
import {
    Route
  } from "react-router-dom";
  
  
const PrivateRoute = ({ component: Component, authenticated, roles, ...rest }) => (
  
    <Route
      {...rest}
      render={props =>
       {
         let check = false;
         if  (!props.loading && authenticated && roles.includes(rest.currentUser.team.teamName )) {
           check =true;
           return ( <Component {...rest} {...props} />)
         } 
         
         else if(!check && !props.loading && authenticated===false){
          return (       
          //props.loading===false ?
          props.history.push('/login', props.location)
          //<Redirect
          //  to={{
          //    pathname: '/login',
           //   state: { from: props.location }
           // }}
         // />
        )
      }
      else if(!check && !props.loading && authenticated && !roles.includes(rest.currentUser.team.teamName)){
        return props.history.push('/unauthorized', props.location)
      }
      
    }
  }
    />
      
);
  
export default PrivateRoute;