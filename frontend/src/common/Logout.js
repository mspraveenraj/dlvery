import {useEffect, useState} from "react"
import { toast } from "react-toastify";
import { ACCESS_TOKEN } from "../constants";
import history from '../history'
const Logout = () => {

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    localStorage.removeItem(ACCESS_TOKEN);
    toast.success("You're safely logged out!", {autoClose: 3000});
    setLoading(false);
  }, [])
  

    return(
      !loading && history.push("/login")
    )

}
export default Logout;