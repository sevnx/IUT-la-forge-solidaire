import axios from 'axios';

const api = axios.create({
  baseURL: 'https://localhost:2020/api',
  withCredentials: true,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
});

export default api;
