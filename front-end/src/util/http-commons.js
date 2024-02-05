import axios from "axios";

const VUE_APP_API_URL = "http://localhost:8080";

const localAxios = () => {
    const instance = axios.create({
        baseURL: VUE_APP_API_URL,
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    })
    return instance;
}
export {localAxios};