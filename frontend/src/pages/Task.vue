<template>
    <v-container>

        <v-layout class="justify-space-between row">
            <router-link :to="`/project/${this.$route.params.projectId}`">
                <v-btn class="py-8 px-0" rounded text>
                    <v-icon class="" color="grey" size="40px">
                        arrow_back_ios
                    </v-icon>
                </v-btn>
            </router-link>

            <router-link
                    :to="{ name: 'TaskForm', params: { projectId:  this.$route.params.projectId, taskId: task.id} }"
            >
                <div>
                    <v-btn text large>Edit task</v-btn>
                </div>
            </router-link>
        </v-layout>

        <v-flex class="d-flex justify-center">
            <div class="text-lg-center">
                <v-card-title primary-title class="layout justify-center">Task</v-card-title>
                <h1>{{task.name}}</h1>
            </div>
        </v-flex>

        <div class="mt-10">
            <b>Description:</b>
            {{task.description}}
            <div class="mt-2">
                <b>Status: </b>{{task.status}}
            </div>
            <h3 class="mt-10">Comments:</h3>
            <comment-list
                    :comments="comments"
                    :task-id="task.id"
                    :task="task"
            >
            </comment-list>
        </div>
    </v-container>
</template>

<script>
    import axios from "axios";
    import CommentList from "../components/comment/CommentList.vue";

    export default {
        name: "Task",
        components: {CommentList},
        data() {
            return {
                task: {}
            }
        },
        computed: {
            comments: function() {
                if(this.task) {
                    return ((this.task.comments) || []).sort((a, b) => (a.id - b.id))
                }
                return null
            }
        },
        methods: {
            async updateTask() {
                axios.get(`http://localhost:8082/projects/${this.$route.params.projectId}/tasks/${this.$route.params.taskId}`)
                    .then((json) => {
                        this.task = json.data
                    })
            }
        },
        watch: {
            '$route'() {
                this.updateTask()
            }
        },
        beforeMount() {
            this.updateTask()
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
</style>