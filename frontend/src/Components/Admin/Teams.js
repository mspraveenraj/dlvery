import * as React from "react";
import {List, Datagrid, TextField, Create, Edit, SimpleForm, TextInput } from 'react-admin';

export const TeamList = props => (
    <List {...props}>
        <Datagrid rowClick="edit">
        
            <TextField source="id" />
            <TextField source="teamName" />
        </Datagrid>
    </List>
);

export const TeamEdit = props => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput disabled  source="id" />
            <TextInput source="teamName" />
        </SimpleForm>
    </Edit>
  );

export const TeamCreate = props => (
   <Create {...props}>
        <SimpleForm>
            <TextInput source="teamName" />
        </SimpleForm>
    </Create>
);