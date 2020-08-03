import axios from 'axios';
import router from '../router';
import EventBus from '../event-bus';

const actions = {
    userSignIn({commit}, payload) {
        const data = {
            username: payload.username,
            password: payload.password,

        };
        commit('setLoading', true);
        axios.post('http://localhost:8082/login', data)
            .then(() => {
                commit('setAuth', true);
                commit('setLoading', false);
                commit('setError', false);
                EventBus.$emit('authenticated', 'User authenticated');
                axios.get('http://localhost:8082/usercreds')
                    .then((json) => {
                        commit('setUser', json.data)
                    })
                router.push('/home');
            })
            .catch(() => {
                commit('setError', true);
                commit('setLoading', false);
            });
    },
    userSignOut({commit}) {
        commit('clearAuth');
        commit('setUser', {})
        EventBus.$emit('authenticated', 'User not authenticated');
        router.push('/signIn');
    },
    userCreds({commit}) {
        axios.get('http://localhost:8082/usercreds')
            .then((json) => {
                commit('setUser', json.data)
            });
    },
    loadProjects({commit}) {
        axios.get('http://localhost:8082/projects')
            .then((json) => {
                commit('setProjects', json.data)
            })
    },
    async createProject({commit}, data) {
        return axios.post('http://localhost:8082/projects', data)
            .then((json) => {
                commit('addProject', json.data)
            })
    },
    async updateProject({ commit }, data) {
        return axios.put(`http://localhost:8082/projects/${data.id}`, data)
            .then((json) => {
                commit('updateProject', json.data)
            })
    },
    async createTask({commit}, data) {
        return axios.post(`http://localhost:8082/projects/${data.id}/tasks`, data)
            .then((json) => {
                commit('createTask', json.data)
            })
    },
    async deleteTask( { commit }, id) {
        return axios.delete(`http://localhost:8082/tasks/${id}`)
            .then(() => {
                commit('deleteTask', id)
            })
    },
    async updateTask({ commit }, payload) {
        return axios.put(`http://localhost:8082/projects/${payload.projectId}/tasks/${payload.id}`, payload)
            .then((json) => {
                commit('updateTask', json.data)
            })
    },
    async isAllowed( { commit }, payload) {
        return axios.get(`http://localhost:8082/projects/${payload.projectId}/participants/${payload.userId}`)
            .then((json) => {
                commit('setIsAllowed', json.data)
            })
    },
    async addCommentAction({commit}, comment) {
        return axios.post(`http://localhost:8082/projects/${comment.projectId}/tasks/${comment.task.id}/comments`, comment)
            .then((json) => {
                commit('addCommentMutation', json.data)
            })
    }
};

export default actions;
