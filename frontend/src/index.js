import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { ToastContainer } from 'react-toastify';
import { Router } from 'react-router';
import history from './history'
import { HashRouter } from 'react-router-dom';
import axios from 'axios';
import { ACCESS_TOKEN } from './constants';

axios.interceptors.request.use(request => {
  request.headers.Authorization = `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`;
  return request;
}, error => {
  return Promise.reject(error);
});


ReactDOM.render(
  <HashRouter>
    <Router history={history}>
      <App />
      <ToastContainer/>
    </Router>
  </HashRouter>
  ,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
