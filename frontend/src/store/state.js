const state = {
  isAuthenticated: localStorage.getItem('auth'),
  user: {},
  error: null,
  loading: false,
  projects: null,
  tasks: null
};

export default state;
