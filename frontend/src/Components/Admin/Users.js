import * as React from "react";
import {List,  downloadCSV, Datagrid, TextField, EmailField, Create, PasswordInput, required, email, Filter, Edit, SimpleForm, ReferenceInput, SelectInput, TextInput } from 'react-admin';
import jsonExport from 'jsonexport/dist';
import HomePageDataService from "../../HomePageDataService";

export const UserList = props =>{   
    
    const exporter = users => {
        const usersForExport = users.map(user => {
            const { password, ...usersForExport } = user;
            //usersForExport.user_name = user.user.name; // add a field
            return usersForExport;
        });
        jsonExport(usersForExport, {
            headers: ['id', 'username', 'firstName', 'lastName', 'team.id', 'team.teamName', 'email', 'contact', 'addreess.id', 'address.addressLine1', 'address.addressLine2', 'address.landmark', 'address.city', 'address.state', 'address.zipcode'], // order fields in the export
            rename: ['User Id', 'Username', 'First Name', 'Last Name', 'Team Id', 'Team Name', 'Email', 'Contact', 'Address Id', 'Address Line 1','Address Line 2', 'Landmark', 'City', 'State', 'Zipcode']
        }, (err, csv) => {
            downloadCSV(csv, 'users'); // download as 'users.csv` file
        });
    };


    const UserFilter = (props) => {
        return (
        <Filter {...props}>
            
            <TextInput label="Username" source="username" alwaysOn />
            <TextInput label="First Name" source="firstName" />
            <ReferenceInput label="Team Name" source="team.id" reference="teams">
                <SelectInput optionText="teamName" />
            </ReferenceInput>
            <TextInput label="Email" source="email" />
            
        </Filter>
    )}

    
    return (
    
    
    <List exporter={exporter} filters={<UserFilter />} {...props}>
        <Datagrid rowClick="edit">
            <TextField source="id" />
            <TextField source="username" />
            <TextField source="firstName" />
            <TextField source="lastName" />
            <TextField source="team.teamName" label ="Team Name"/>
            <EmailField source="email" />
            <TextField source="contact" />
           
        </Datagrid>
    </List>
)}




export const UserEdit = props => (
    <Edit {...props}>
        <SimpleForm >
          
            <TextInput disabled  source="id" />
            <TextInput disabled source="username" autoComplete="username"/>
            <TextInput source="firstName" validate={[required()]}/>
            <TextInput source="lastName" validate={[required()]}/>
            <ReferenceInput label="Team Name" source="team.id" reference="teams" validate={[required()] }>
                <SelectInput optionText="teamName" />
            </ReferenceInput>
            <TextInput source="email" validate={[required(), email()]}/>
            <TextInput source="contact" />
            <PasswordInput source = "password" autoComplete="current-password" />
           
        </SimpleForm>
    </Edit>
  );

  
  const validateUsernameUnicity = async (value) => {
    let isUsernameUnique = false;
     await HomePageDataService.checkUsernameIsUnique(value)
     .then( res => isUsernameUnique = res.data)
     
     if( isUsernameUnique ){
        return 'Username is already used';
    }
}

const validateEmailUnicity = async (value) => {
    let isEmailUnique = false;
     await HomePageDataService.checkEmailIsUnique(value).then(
     res => isEmailUnique = res.data)
     
     if( isEmailUnique ){
        return 'Email is already used';
     }
}

export const UserCreate = props => (
   <Create {...props}>
        <SimpleForm >
            <TextInput source="username" validate={[required(), validateUsernameUnicity]} autoComplete="username"/>
            <TextInput source="firstName" validate={[required()]}/>
            <TextInput source="lastName" validate={[required()]}/>
            <ReferenceInput label="Team Name" source="team.id" reference="teams" validate={[required()]}>
                <SelectInput optionText="teamName" />
            </ReferenceInput>
            <TextInput source="email" validate={[required(), email(), validateEmailUnicity]}/>
            <TextInput source="contact" />
            <PasswordInput source="password" validate={[required()]} autoComplete="current-password"/>
        </SimpleForm>
    </Create>
);