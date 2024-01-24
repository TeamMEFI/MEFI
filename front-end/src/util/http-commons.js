import axios from "axios";

const { VUE_APP_API_URL } = import.meta.env;

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