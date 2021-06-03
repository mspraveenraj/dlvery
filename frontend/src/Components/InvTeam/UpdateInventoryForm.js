import React from 'react'
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import moment from 'moment'
import HomePageDataService from '../../HomePageDataService';
import { toast } from 'react-toastify';
import history from '../../history';
import LoadingIndicator from '../../common/LoadingIndicator';

class UpdateInventoryForm extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            id : this.props.match.params.id,  
            inventory: {
                productCategory: {name: ''},
                user: {username: ''},
                productStatus:{status: ''},
                dateIn: new Date(),
                dateOut: new Date(),
                expiryDate: new Date(),
                customerSignature: null
            },
            productCategoryList: [],
            productStatusList: [],
            deliveryAgentsList: [],
            inventoryLoading: true,
            productCategoryLoading: true,
            productStatusLoading: true,
            deliveryAgentsLoading: true
          }
    
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleDate = this.handleDate.bind(this);
    this.handleList = this.handleList.bind(this);
    this.handleDeliveryAgent = this.handleDeliveryAgent.bind(this);
    }

   componentDidMount() {
        
        HomePageDataService.retrieveInventory(this.state.id)
            .then(response => {
              response &&  this.setState({...this.state, inventory : response.data , inventoryLoading: false } )
            })

          HomePageDataService.retrieveAllProductCategory()
            .then(response => {
              this.setState({...this.state, productCategoryList: response.data, productCategoryLoading: false});
            }).then(this.setState ( { ...this.state, productCategoryLoading : false}));

          HomePageDataService.retrieveAllProductStatus()
            .then(response => {
              this.setState({...this.state, productStatusList: response.data, productStatusLoading: false});
            });

          HomePageDataService.retrieveAllDeliveryAgents()
            .then(response => {
              this.setState({...this.state, deliveryAgentsList: response.data, deliveryAgentsLoading: false});
            })

    }


    handleChange = (event) => {
      this.setState( {...this.state, inventory: {...this.state.inventory, [event.target.name]: event.target.value} } );
    }

    handleDate = (date, name) => {
        let postDate;
        if(moment(date, 'DD/MM/YYYY').isValid())
        {   
            postDate = moment(date).format("DD/MM/YYYY");
        }
      this.setState( {...this.state, inventory: {...this.state.inventory, [name]: postDate} } );
    }

    handleList = (event) => {
      this.setState({...this.state, inventory: {...this.state.inventory, [event.target.name]: {id: event.nativeEvent.target.selectedIndex+1, [event.target.title] : event.target.value}}});
    }

    handleDeliveryAgent = (event) => {
      this.setState({...this.state, inventory: {...this.state.inventory, user: { ...this.state.deliveryAgentsList[event.nativeEvent.target.selectedIndex], [event.target.name]: event.target.value}}});
    }

    handleSubmit = (props)=> {
        props.preventDefault();
        toast.success("Inventory Updated", {autoClose: 3000})
        setTimeout( () =>{
          HomePageDataService.putInventory(this.state.inventory, this.state.id)
          .then(history.push(`/invTeamPage`));
                        }, 3000);
       
    }

  render() {
    require("./UpdateInventoryForm.css")
    const inventory = this.state.inventory;
    let invDateIn;
    let invDateOut;
    let invExpiryDate;
    (inventory.dateIn === null || inventory.dateIn === undefined) ?  invDateIn = undefined : invDateIn = new Date( moment(inventory.dateIn, 'DD-MM-YYYY'));
    (inventory.dateOut === null || inventory.dateOut === undefined) ?  invDateOut = undefined : invDateOut = new Date( moment(inventory.dateOut, 'DD-MM-YYYY'));
    (inventory.expiryDate === null || inventory.expiryDate === undefined) ? invExpiryDate = undefined : invExpiryDate = new Date( moment(inventory.expiryDate, 'DD-MM-YYYY'));
   

    const form =
    <div id="updateInventoryForm-box">
    <div className="updateInventoryFormLeft">
      <h1> Update Inventory </h1>
        <form onSubmit={this.handleSubmit}>
        
        <label>
          <p>Inventory Id</p>
        </label>
          <input type="text" name="id" onChange={this.handleChange} value={inventory.id || ''} disabled/>
    <br/>
        <label>
          <p>SKU</p>
        </label>
          <input type="text" name="sku" onChange={this.handleChange} value={inventory.sku || ''} disabled/>
      <br/>

        <label>
          <p>Product Name</p>
        </label>
          <input type="text" name="productName" onChange={this.handleChange} value={inventory.productName || ''} autoComplete="off" required/>
    <br/>
        <label>
          <p>Product Category</p>
        </label>
      <br/>
          <select type="text" name="productCategory" title = "name" onChange={this.handleList} value={inventory.productCategory.name || ''} required>
            { 
              this.state.productCategoryList.map( (category) =>
              
                <option key= {category.id} value={category.name}>{category.name}</option>
               
              )
            }
          </select>
    <br/><br/><br/>
        <label>
          <p>Date In</p>
        </label>
          <DatePicker type="date"
              selected={ invDateIn }
              onChange={ (date) => this.handleDate(date, "dateIn") }
              name="dateIn"
              dateFormat="dd/MM/yyyy"
              closeOnScroll={true}
              shouldCloseOnSelect={true}
              autoComplete="off"
              required
            />
        <br/><br/><br/>
      
        <label>
          <p>Date Out</p>
        </label>
          <DatePicker type="date"
              selected={ invDateOut }
              onChange={ (date) => this.handleDate(date, "dateOut") }
              name="dateOut"
              dateFormat="dd/MM/yyyy"
              closeOnScroll={true}
              shouldCloseOnSelect={true}
              autoComplete="off"
            />
    <br/><br/><br/>
        <label>
          <p>Expiry Date</p>
        </label>
          <DatePicker type="date"
              selected={ invExpiryDate }
              onChange={ (date) => this.handleDate(date, "expiryDate") }
              name="expiryDate"
              dateFormat="dd/MM/yyyy"
              closeOnScroll={true}
              shouldCloseOnSelect={true}
              autoComplete="off"
            />
    <br/><br/><br/>
        <label>
          <p>Delivery Agent</p>
        </label>
        <br/>
          <select type="select" name="username" onChange={this.handleDeliveryAgent} value={inventory.user.username || ''} required>
          { 
              this.state.deliveryAgentsList.map( (deliveryAgent) =>
              
                <option key= {deliveryAgent.id} value={deliveryAgent.username}>{deliveryAgent.username}</option>
              )
          } 
          </select>
     <br/><br/><br/>
        <label>
          <p>Product Status</p>
        </label>
        <br/>
          <select type="select" name="productStatus" title = "status" onChange={this.handleList} value={inventory.productStatus.status || ''} required>
            { 
              this.state.productStatusList.map( (pstatus) =>
              
                <option key= {pstatus.id} value={pstatus.status}>{pstatus.status}</option>
              )
            }
          </select>
    <br/><br/><br/>
        <label>
          <p>Customer Name</p>
          <input type="text" name="customerName" onChange={this.handleChange} value={inventory.customerName || ''} autoComplete="off"/>
        </label>
     <br/>
        <label>
          <p>Customer Signature</p>
        </label>
      <br/>
      {this.state.inventory.customerSignature ? (
        <img
          src={this.state.inventory.customerSignature}
          alt="customer signature"
          style={{
            
            margin: "0 auto",
            border: "1px solid black",
            width: "150px"
          }}
        />
      ) : null}
        
    
      <button type="submit">Submit</button>
    </form>
      </div>
      </div>

const content =  (this.state.inventoryLoading && this.state.productCategoryLoading && this.state.productStatusLoading && this.state.deliveryAgentsLoading)
        ? <LoadingIndicator/> : form ;

return (
    <>
    <div style={{marginTop: "60px"}}>{content}</div>
   </>
    )
  }
}
export default UpdateInventoryForm;