<template>
    <v-container>
        <div align="center">Project
            <h1>{{project.name}}</h1>
        </div>
        <div class="mt-10">
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
            async updateProject() {
                axios.get(`http://localhost:8082/projects/${this.$route.params.id}`)
                    .then((json) => {
                        this.project = json.data
                    })
            }
        },
        watch: {
            '$route'() {
                this.updateProject()
            }
        },
        beforeMount() {
            this.updateProject()
        }
    }
</script>

<style scoped>
</style>