import React from 'react';
import { Admin, Resource, fetchUtils } from 'react-admin';
import Dashboard from './Dashboard';
import { UserList, UserEdit, UserCreate } from './Users';
import { TeamList, TeamEdit, TeamCreate } from './Teams';
import { ProductCategoryList, ProductCategoryEdit, ProductCategoryCreate } from './ProductCategory';
import jsonServerProvider from 'ra-data-json-server';
import { Person, Group, AddBoxTwoTone } from '@material-ui/icons';
import { ACCESS_TOKEN } from '../../constants';
import AdminLogin from './AdminLogin';

  const httpClient = (url, options = {}) => {
    if (!options.headers) {
        options.headers = new Headers({ Accept: 'application/json' });
    }
    const token = localStorage.getItem(ACCESS_TOKEN);
    options.headers.set('Authorization', `Bearer ${token}`);
    return fetchUtils.fetchJson(url, options);
}

const dataProvider = jsonServerProvider("http://localhost:8070", httpClient);

const AdminPage = () => (
    <Admin dashboard={Dashboard} dataProvider={dataProvider} loginPage={AdminLogin} >
        <Resource name="users" list={UserList} edit={UserEdit} create = {UserCreate} icon={Person}/>
        <Resource name="teams" list={TeamList} edit={TeamEdit} create = {TeamCreate} icon={Group}/>
        <Resource name="productCategory" list={ProductCategoryList} edit={ProductCategoryEdit} create = {ProductCategoryCreate} icon={AddBoxTwoTone}/>
    </Admin>
);

export default AdminPage;