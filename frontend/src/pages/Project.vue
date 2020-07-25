<template>
    <v-container>
        <v-layout class="justify-space-between row">
            <router-link :to="`/main`">
                <v-btn class="py-8 px-0" rounded text>
                    <v-icon class="" color="grey" size="40px">
                        arrow_back_ios
                    </v-icon>
                </v-btn>
            </router-link>

            <v-flex class="d-flex justify-center ml-16">
                <div class="text-lg-center">
                    <v-card-title primary-title class="layout justify-center">Project</v-card-title>
                    <h1>{{project.name}}</h1>
                </div>
            </v-flex>

            <router-link
                    :to="{ name: 'TaskForm', params: { projectId: project.id } }"
            >
                <div>
                    <v-btn text large>Add task</v-btn>
                </div>
            </router-link>

            <router-link
                    :to="{ name: 'ProjectForm', params: { isProjectPage, id: project.id } }"
            >
                <div v-if="isManager">
                    <v-btn text large>Edit project</v-btn>
                </div>
            </router-link>
        </v-layout>

        <div class="mt-16">
            <b>Description:</b>
            {{project.description}}
            <div class="mt-2">
                <b>Status: </b>{{project.status}}
            </div>
            <h3 class="mt-10">Tasks:</h3>
            <div v-if="this.$store.state.tasks && this.$store.state.tasks.length">
                <task-list
                        :tasks="this.$store.state.tasks"
                ></task-list>
            </div>
            <div v-else>Empty list.</div>
        </div>

    </v-container>
</template>

<script>
    import axios from "axios";
    import TaskList from "../components/task/TaskList.vue";
    import {mapGetters} from "vuex";

    export default {
        name: "Project",
        components: {
            TaskList
        },
        computed: {
            ...mapGetters(['getUser']),
            isManager: function () {
                if (this.getUser && this.getUser.roles) {
                    return this.getUser.roles.some(role => role === 'MANAGER')
                }
                return false
            }
        },
        data() {
            return {
                project: {},
                isProjectPage: true
            }
        },
        methods: {
            async loadProject() {
                axios.get(`http://localhost:8082/projects/${this.$route.params.id}`)
                    .then((json) => {
                        this.project = json.data
                        this.$store.state.tasks = this.project.tasks
                    })
                this.$forceUpdate()
            }
        },
        watch: {
            '$route'() {
                this.loadProject()
            }
        },
        beforeMount() {
            this.loadProject()
        },
        beforeDestroy() {
            this.$store.state.tasks = null
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
</style>