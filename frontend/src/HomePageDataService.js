import axios from 'axios'
import { toast } from 'react-toastify';

//const INSTRUCTOR = 'in28minutes'
const COURSE_API_URL = 'https://dlvery-deployment.herokuapp.com'
//const INSTRUCTOR_API_URL = `${COURSE_API_URL}/listInventories/${INSTRUCTOR}`
const INVENTORY_API_URL = `${COURSE_API_URL}`

class HomePageDataService {

    async retrieveAllInventories() {
        return await axios.get(`${INVENTORY_API_URL}/inventory`).catch(() => {toast.error("List All Inventory Failed", {autoClose: 3000, delay:3000})});
    }

    async retrieveInventory(id) {
        return await axios.get(`${INVENTORY_API_URL}/inventory/${id}`).catch(() => {toast.error("Retrieve Inventory Failed", {autoClose: 3000, delay:3000})});
    }

    async retrieveAllProductCategory() {
        return await axios.get(`${INVENTORY_API_URL}/productCategory`).catch(() => {toast.error("Retreive Product Category Failed", {autoClose: 3000, delay:3000})});
    }

    async retrieveAllProductStatus() {
        return await axios.get(`${INVENTORY_API_URL}/productStatus`, ).catch(() => {toast.error("Retreive Product Status Failed", {autoClose: 3000, delay:3000})});
    }

    async retrieveAllDeliveryAgents() {
        return await axios.get(`${INVENTORY_API_URL}/dlTeam`).catch(() => {toast.error("Retreive Delivery Agents Failed", {autoClose: 3000, delay:3000})});
    }

    async putInventory(props, id) {
        return await axios.put(`${INVENTORY_API_URL}/inventory/${id}`, props).catch(() => {toast.error("Update Inventory Failed", {autoClose: 3000, delay:3000})});
    }

    async createInventory(props) {
        return await axios.post(`${INVENTORY_API_URL}/inventory`, props).catch(() => {toast.error("Add to Inventory Failed", {autoClose: 3000, delay:3000})});
    }
    // /ivewInventory
    async viewAndExportReadyInvenories() {
        return await axios.get(`${INVENTORY_API_URL}/inventory/view`).catch(() => {toast.error("List All Inventory Failed", {autoClose: 3000, delay:3000})});
    }

    async postUploadExcelFile(props) {
        return await axios.post(`${INVENTORY_API_URL}/inventory/uploadFile`, props).catch(() => {toast.error("Upload to Server Failed", {autoClose: 3000, delay:3000})});
    }

    //DLTeamPage

    async retrieveAllInventoriesByDeliveryAgent(username) {
        return await axios.get(`${INVENTORY_API_URL}/dlTeam/inventory/${username}`).catch(() => {toast.error("List All Inventory Failed", {autoClose: 3000, delay:3000})});
    }

    async putDLTeamInventory(props, id) {
        return await axios.put(`${INVENTORY_API_URL}/dlTeam/inventory/${id}`, props).catch(() => {toast.error("Update Inventory Failed", {autoClose: 3000, delay:3000})});
    }

    //AdminPage

    async retrieveAllAdmins() {
        return await axios.get(`${INVENTORY_API_URL}/adminTeam`).catch(() => {toast.error("Retreive Admins Failed", {autoClose: 3000, delay:3000})});
    }

    async retrieveAllTeams() {
        return await axios.get(`${INVENTORY_API_URL}/teams`).catch(() => {toast.error("Retreive Teams Failed", {autoClose: 3000, delay:3000})});
    }

    async checkUsernameIsUnique(username) {
        return await axios.get(`${INVENTORY_API_URL}/users/checkUsernameUnique/${username}`).catch(() => {toast.error("Retreive username unique Failed", {autoClose: 3000, delay:3000})});
    }

    async checkEmailIsUnique(email) {
        return await  axios.get(`${INVENTORY_API_URL}/users/checkEmailUnique/${email}`).catch(() => {toast.error("Retreive email unique Failed", {autoClose: 3000, delay:3000})});
    }

    //Profile

    async updateCurrentUserAddress(id, props) {
        return await axios.put(`${INVENTORY_API_URL}/users/changeAddress/${id}`, props).catch(() => {toast.error("Change Address Failed", {autoClose: 3000, delay:3000})});
    }

    async updateCurrentUserPassword(id, props) {
        return await axios.put(`${INVENTORY_API_URL}/users/changePassword/${id}`, props,).catch(() => {toast.error("Change Password Failed", {autoClose: 3000, delay:3000})});
    }



}

export default new HomePageDataService();
