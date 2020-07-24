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

            <v-flex class="d-flex justify-center">
                <div class="text-lg-center">
                    <v-card-title primary-title class="layout justify-center">Project</v-card-title>
                    <h1>{{project.name}}</h1>
                </div>
            </v-flex>
        </v-layout>

        <div class="mt-16">
            <b>Description:</b>
            {{project.description}}
            <div class="mt-2">
                <b>Status: </b>{{project.status}}
            </div>
            <h3 class="mt-10">Tasks:</h3>
            <div v-if="project.tasks && project.tasks.length">
                <task-list
                        :tasks="project.tasks"
                ></task-list>
            </div>
            <div v-else>Empty list.</div>
        </div>
    </v-container>
</template>

<script>
    import axios from "axios";
    import TaskList from "../components/task/TaskList.vue";

    export default {
        name: "Project",
        components: {
            TaskList
        },
        data() {
            return {
                project: {}
            }
        },
        methods: {
            async loadProject() {
                axios.get(`http://localhost:8082/projects/${this.$route.params.id}`)
                    .then((json) => {
                        this.project = json.data
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
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
</style>