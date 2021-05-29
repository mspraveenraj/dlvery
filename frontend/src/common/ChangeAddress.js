import React from 'react';
import { toast } from 'react-toastify';
import HomePageDataService from '../HomePageDataService';
import LoadingIndicator from './LoadingIndicator';

class ChangeAddress extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          address: {
            addressLine1: '',
            addressLine2: '',
            landmark: '',
            city: '',
            state: '',
            zipcode: ''
          },
            loading: true
        }
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {

        this.props.currentUser!== undefined && this.props.currentUser.address!== undefined && this.setState({address: {...this.props.currentUser.address}, loading: false})
        
    }

    handleChange = (event) => {
        this.setState( {...this.state, address:{...this.state.address, [event.target.name]: event.target.value} } );
    }

    handleSubmit = (event) => {
        event.preventDefault();
        HomePageDataService.updateCurrentUserAddress(this.props.currentUser.id, this.state.address) 
            .then(toast.success("Address Updated!", {autoClose: 3000}));
    }
    render() {
      require("./ChangeAddress.css");
        
      const form =
      
      <div id="address-box">
        <div className="left">
        <h1>Change Address</h1>
        
        <form onSubmit={this.handleSubmit}>
        
          <input type="text" name="addressLine1" onChange={this.handleChange} value={this.state.address.addressLine1 || ''} placeholder="Address Line 1"/>
        
          <input type="text" name="addressLine2" onChange={this.handleChange} value={this.state.address.addressLine2 || ''} placeholder="Address Line 2"/>
  
          <input type="text" name="landmark" onChange={this.handleChange} value={this.state.address.landmark || ''} placeholder="Landmark"/>
       
          <input type="text" name="city" onChange={this.handleChange} value={this.state.address.city || ''} placeholder="City"/>
    
          <input type="text" name="state" onChange={this.handleChange} value={this.state.address.state || ''} placeholder="State"/>  
    
          <input type="text" name="zipcode" onChange={this.handleChange} value={this.state.address.zipcode || ''} placeholder="Zipcode"/>
    
          <button type="submit">Submit</button>
      </form>
      </div>
    </div>
const content =  (this.state.loading)
        ? <LoadingIndicator/> : form ;

return (
    <>
    <div>{content}</div>
   </>
    )
  }
}

export default ChangeAddress;