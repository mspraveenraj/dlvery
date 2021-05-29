import React, { Component } from 'react';

import { Link } from 'react-router-dom';

class NotFound extends Component {
    render() { require("./NotFound.css")
        return ( 
<section className="page_404">
    <div className="row"> 
    <div className="col-sm-12 ">
    <div className="col-sm-10 col-sm-offset-1  text-center">
    <div className="four_zero_four_bg">
      <h1 className="text-center ">404</h1>
    
    
    </div>
    <br/><br/>
    <div className="contant_box_404">
    <h3 className="h2">
        Look like you're lost
    </h3>
    
    <p className="sub_text">the page you are looking for not avaible!</p>
    
    <Link to="/"><button className="link_404" type="button">Go to Home</button></Link>
    
  </div>
    </div>
    </div>
    </div>
 
</section>
               
           
        );
    }
}

export default NotFound;