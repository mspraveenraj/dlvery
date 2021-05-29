import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class Unauthorized extends Component {
    render() { require("./Unauthorized.css");
        return (
    <section className="page_404">
        <div className="row"> 
        <div className="col-sm-12 ">
        <div className="col-sm-10 col-sm-offset-1  text-center">
        <div className="four_zero_four_bg">
          <h1 className="text-center ">403</h1>
        
        
        </div>
        <br/><br/>
        <div className="contant_box_404">
        <h3 className="h2">
            Access Denied
        </h3>
        
        <p className="sub_text">the page you are looking for not avaible!</p>
        
        <Link to="/"><button className="link_404" type="button">Go to Home</button></Link>
        
      </div>
        </div>
        </div>
        </div>
    </section>
                
        )
    }
}

export default Unauthorized;