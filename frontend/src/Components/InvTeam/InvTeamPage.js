import React from "react"
import { DataGrid, GridToolbarContainer, GridToolbarExport, GridColumnsToolbarButton, GridFilterToolbarButton } from '@material-ui/data-grid';
import HomePageDataService from "../../HomePageDataService";
import history from '../../history';
import { toast } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import "../../common/reset.css"
class InvTeamPage extends React.Component {
    
    constructor(props) {
        super(props);
        this.state = {
            inventories: [{
                id: '', 
                productName: '', 
                productCategory: {name: 'Essential'},
                productStatus: {status: ''},
                user: { username: 'raj'}
            }
            ],
            loading: true,
            uploadExcelFile: '',
            uploadExcelFileName: ''
        }
      this.refreshInventories = this.refreshInventories.bind(this)
      this.updateInventoryClicked = this.updateInventoryClicked.bind(this)
      this.addInventoryClicked = this.addInventoryClicked.bind(this)
      this.handleUploadFileChange = this.handleUploadFileChange.bind(this);
      this.handleUploadFile = this.handleUploadFile.bind(this)
    }
    componentDidMount() {
        this.refreshInventories();
    }
   
    async refreshInventories() {
        this.state.loading = true;
       await HomePageDataService.viewAndExportReadyInvenories()
            .then(
                response => {
                   response &&  this.setState({ loading: false, inventories: response.data})
                }
            )//.catch ( history.push("/unauthorized"));


    }
    
    addInventoryClicked() {
        history.push(`/invTeamPage/addInventoryForm`)
    }


    updateInventoryClicked(id) {
        this.props.history.push(`/invTeamPage/updateInventoryForm/${id}`)
    }

    handleUploadFileChange(event) {
      this.setState({...this.state, uploadExcelFileName: event.target.value, uploadExcelFile: event.target.files[0]})
    }
      
    handleUploadFile(event){
      event.preventDefault();
      const validExt = ".xlsx";
      const fileData = this.state.uploadExcelFileName;
      
      const getFileExt = fileData.split('.').pop();
      if(fileData === '') {
        toast.error("No File Selected", {autoClose: 3000});
      }
      else if(validExt.indexOf(getFileExt) < 0) {
        toast.error("Invalid File Format", {autoClose: 3000});
      }
      else {
        const formData = new FormData();
        formData.append("file", this.state.uploadExcelFile);
      
         HomePageDataService.postUploadExcelFile(formData)
          .then(response => (response && response.data) ? toast.success("Data Updated!", {autoClose: 3000}) : toast.error("Data Update failed by Server", {autoClose: 3000}) )
          setTimeout(()=> {
            window.location.reload();
          }, 3000)
      }
        
    }
    render() {
      require("./InvTeamPage.css");
        

        let idValue;
        const columns = [
            {field: "", headerName: "Update", width: 100, filterable: false, sortable: false, disableClickEventBubbling: true,
            
                    renderCell: (params) => {
                        const onClick = () => {
                          const api = params.api;
                          const fields = api
                            .getAllColumns()
                            .map((c) => c.field)
                            .filter((c) => c !== "__check__" && !!c && c==="id");
                  
                          fields.forEach((f) => {
                            idValue = params.getValue(f);
                          });
                  
                          return this.updateInventoryClicked(idValue);
                        };
                  
                        return <button className="btn" onClick={onClick}>Update</button>;
                      }
            },
            { field: "id", headerName: "Id", type: "number"},
            { field: "sku", headerName: "SKU", width: 110, type: 'string'},
            { field: "productName", headerName: "Product Name", width: 250, type: 'string' },
            { field: "productCategory", headerName: "Category", width: 150, type: 'string' },
            { field: "productStatus", headerName: "Status", width: 150, type: 'string' },
            { field: "dateIn", headerName: "Date In", width: 130, type: 'date' },
            { field: "dateOut", headerName: "Date Out", width: 130, type: 'date' },
            { field: "expiryDate", headerName: "Expiry Date", width: 130, type: 'date' },
            { field: "username", headerName: "Delivery Agent", width: 150, type: 'string', sortable: false },
            { field: "customerName", headerName: "Customer Name", width: 140, type: 'string' },
            { field: "customerSignature", headerName: "Customer Signature", width: 160, type: 'string' },
            { field: "entryBy", headerName: "Entry By", width: 100, type: 'string' },
            { field: "entryDate", headerName: "Entry Date", width: 100, type: 'string' }
    
          ];

          
          function CustomToolbar() {
            return (
              <GridToolbarContainer>
                <GridColumnsToolbarButton />
                <GridFilterToolbarButton />
                <GridToolbarExport />
              </GridToolbarContainer>
            );
          }
        

        return (
          <>
          
         
        <div className="invTeamPage-container">
            <h3>Inventory List</h3>
            <br/><br/>
               <div className="btn-container">
               
                <button id="inner" className="block" onClick={this.addInventoryClicked}>Add</button>
                
                    <div id="inner" className="button-wrap">
                      <label className ="new-button" htmlFor="upload"> Choose File </label>
                      <input id="upload" type="file"  name="uploadFile" accept=".xlsx" onChange={this.handleUploadFileChange}/>
                    </div>
                
                  <button id="inner" className="block" onClick={this.handleUploadFile}>Upload</button>
              
            </div>
            
            <div className="datagrid-container" style={{ width: "100%" }}>
            <DataGrid rows = {this.state.inventories} 
            columns = {columns}
        
            components={{
                    Toolbar:  CustomToolbar,
                  }}
            sortModel={[
              {
                field: 'id',
                sort: 'desc',
              }]}
            loading = {this.state.loading}
            autoHeight = {true}
            autoPageSize = {true}
            disableColumnMenu = {true}
            hideFooterSelectedRowCount = {true}
            
            />
            </div>
        </div>
        </>
        )
        
    }
                        
}

export default InvTeamPage;