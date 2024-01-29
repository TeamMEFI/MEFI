import axios from "axios";

const VUE_APP_API_URL = process.env.VUE_APP_API_URL;

const localAxios = () => {
    console.log(VUE_APP_API_URL)
    const instance = axios.create({
        baseURL: VUE_APP_API_URL,
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    })
    return instance;
}
export {localAxios};