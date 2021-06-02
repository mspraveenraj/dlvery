import React, { useEffect, useState, useContext } from "react"
import { DataGrid } from '@material-ui/data-grid';
import DLTeamContext from './DLTeamContext'
import history from '../../history';

const DLTeamDeliveryPriority = (props) => {
    require("./DLTeamDelivery.css");

    const [inventoryData, setInventoryData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);   
    
    const value = useContext(DLTeamContext);    
    let priorityDelivery = [];
    const refreshInventories = () => {
        setIsLoading(true);
        Array.prototype.forEach.call(value,
           (inventory) => {
               if((inventory.productStatus.status === 'Pending' || inventory.productStatus.status === 'OnTransit') && (inventory.productCategory.name === 'Perishable' || inventory.productCategory.name === 'Emergency'))
               {
                   priorityDelivery.push(inventory);
               }
           }
        
       )
           setInventoryData(priorityDelivery);
           setIsLoading(false);
    }

    useEffect(
        refreshInventories
        
    , []
    )
    
   
    const updateInventoryClicked = (id) => {
        history.push(`/dlTeamPage/inventory/${id}`)
    }

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
                  
                          return updateInventoryClicked(idValue);
                        };
                  
                        return <button className="btn" onClick={onClick}>Update</button>;
                      }
            },
            { field: "id", headerName: "Id", type: "number"},
            { field: "sku", headerName: "SKU", width: 110, type: 'string'},
            { field: "productName", headerName: "Product Name", width: 325, type: 'string' },
            { field: "productCategory", headerName: "Category", width: 150, type: 'string', valueFormatter: ({ value }) => value.name },
            { field: "productStatus", headerName: "Status", width: 150, type: 'string', valueFormatter: ({ value }) => value.status },
            { field: "dateIn", headerName: "Date In", width: 130, type: 'date' },
            { field: "dateOut", headerName: "Date Out", width: 130, type: 'date' },
            { field: "expiryDate", headerName: "Expiry Date", width: 130, type: 'date' },
            { field: "customerName", headerName: "Customer Name", width: 200, type: 'string' },
            { field: "customerSignature", headerName: "Customer Signature", width: 190, type: 'string' }
    
          ];

        return (
      
        <div className="dlTeam-container">
            <h3 style={{marginLeft: '20px', fontSize: '20px', marginTop: '30px', marginBottom: '30px'}}>Priority Deliveries</h3>
            
            <div className="datagrid-container">
            <DataGrid rows = {inventoryData} 
            columns = {columns}
            pageSize = {25}
            loading = {isLoading}
            autoHeight = {true}
            autoPageSize = {true}
            disableColumnMenu = {true}
            hideFooterSelectedRowCount = {true}
            />
            </div>
        </div>

        )
           
                    
}

export default DLTeamDeliveryPriority;