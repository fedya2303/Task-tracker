const state = {
  isAuthenticated: localStorage.getItem('auth'),
  user: {},
  error: false,
  loading: false,
  projects: null,
  tasks: null,
  isAllowed: false
};

export default state;
