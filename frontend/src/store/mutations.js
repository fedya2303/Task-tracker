const mutations = {
  setUser(state, payload) {
    state.user = payload;
  },
  setAuth(state, payload) {
    localStorage.setItem('auth', payload);
  },
  clearAuth() {
    localStorage.removeItem('auth');
  },
  setError(state, payload) {
    state.error = payload;
  },
  setLoading(state, payload) {
    state.loading = payload;
  },
  setProjects(state, payload) {
    state.projects = payload;
  },
  addProject(state, payload) {
    if (state.projects) {
      state.projects = [
          ...state.projects,
          payload
      ]
    } else {
      state.projects = [ payload ]
    }
  }
};

export default mutations;
