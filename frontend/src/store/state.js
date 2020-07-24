const state = {
  isAuthenticated: localStorage.getItem('auth'),
  user: {},
  error: null,
  loading: false,
  projects: null
};

export default state;
