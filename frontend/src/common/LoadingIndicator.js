import React from 'react';

export default function LoadingIndicator(props) {
    require("./LoadingIndicator.css");
    return (
        //<div className="loading-indicator" style={{display: 'block', textAlign: 'center', marginTop: '30px'}}>
         //   Loading ...
       // </div>
        <div className="spinner">
        <div className="cube1"></div>
        <div className="cube2"></div>
      </div>
    );
}