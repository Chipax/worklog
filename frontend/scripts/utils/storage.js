export const storage = {
    getToken: () => localStorage.getItem('token'),
    setToken: (token) => localStorage.setItem('token', token),
    removeToken: () => localStorage.removeItem('token'),
    
    getUser: () => localStorage.getItem('username'),
    setUser: (name) => localStorage.setItem('username', name),
    
    clearSession: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('username');
    }
};