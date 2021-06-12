import React from 'react'
import PropTypes from 'prop-types';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Box from '@material-ui/core/Box';
import DLTeamDeliveryPriority from './DLTeamDeliveryPriority'
import DLTeamDeliveryPending from './DLTeamDeliveryPending'
import DLTeamDeliveryAll from './DLTeamDeliveryAll'

function TabPanel(props) {
    const { children, value, index, ...other } = props;
  
    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        id={`full-width-tabpanel-${index}`}
        aria-labelledby={`full-width-tab-${index}`}
        {...other}
      >
        {value === index && (
          <Box>
            {children}
          </Box>
        )}
      </div>
    );
  }
  
  TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
  };
  
  function a11yProps(index) {
    return {
      id: `full-width-tab-${index}`,
      'aria-controls': `full-width-tabpanel-${index}`,
    };
  }
  
  const useStyles = makeStyles((theme) => ({
    root: {
      backgroundColor: theme.palette.background.paper,
      //width: 500,
      marginTop: "60px"
    },
  }));


const DLTeamPage = (props) => {
     require("./DLTeamPage.css");
    
    const classes = useStyles();
    const theme = useTheme();
    const [value, setValue] = React.useState(0);
  
    const handleChange = (event, newValue) => {
      setValue(newValue);
    };
  
    //const handleChangeIndex = (index) => {
     // setValue(index);
    //};

    return(
      

      <div className={classes.root}>
      <AppBar position="static" color="default">
        <Tabs
          value={value}
          onChange={handleChange}
          indicatorColor="primary"
          textColor="primary"
          variant="fullWidth"
          aria-label="full width tabs"
        >
          <Tab label="Priority" {...a11yProps(0)} />
          <Tab label="Pending" {...a11yProps(1)} />
          <Tab label="All" {...a11yProps(2)} />
        </Tabs>
      </AppBar>
      
        <TabPanel value={value} index={0} dir={theme.direction}>
          <DLTeamDeliveryPriority userValue = {props}/>
        </TabPanel>
        <TabPanel value={value} index={1} dir={theme.direction}>
          <DLTeamDeliveryPending userValue = {props}/>
        </TabPanel>
        <TabPanel value={value} index={2} dir={theme.direction}>
          <DLTeamDeliveryAll userValue = {props}/>
        </TabPanel>
 
      </div>

    )
}


export default DLTeamPage;