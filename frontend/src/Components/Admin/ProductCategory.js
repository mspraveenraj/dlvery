import * as React from "react";
import {List, Datagrid, TextField, Create, Edit, SimpleForm, TextInput } from 'react-admin';

export const ProductCategoryList = props => (
    <List {...props}>
        <Datagrid rowClick="edit">
        
            <TextField source="id" />
            <TextField source="name" />
        </Datagrid>
    </List>
);

export const ProductCategoryEdit = props => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput disabled  source="id" />
            <TextInput source="name" />
        </SimpleForm>
    </Edit>
  );

export const ProductCategoryCreate = props => (
   <Create {...props}>
        <SimpleForm>
            <TextInput source="name" />
        </SimpleForm>
    </Create>
);