import axios from "axios";

// 設定baseURL (如果有的話可以設定，沒有可以刪掉)
// axios.defaults.baseURL = "http://你的後端api伺服器";

// 加Request攔截器
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token"); // 從localStorage取得token
    if (token) {
      config.headers.Authorization = `Bearer ${token}`; // 在headers加上Authorization
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
