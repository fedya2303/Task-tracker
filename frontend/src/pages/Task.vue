<template>
    <v-container>
        <div align="center">Task
            <h1>{{task.name}}</h1>
        </div>
        <div class="mt-10">
            <b>Description:</b>
            {{task.description}}
            <div class="mt-2">
                <b>Status: </b>{{task.status}}
            </div>
            <h3 class="mt-10">Comments:</h3>
            <comment-list
                :comments="task.comments"
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
        methods: {
            async updateTask() {
                axios.get(`http://localhost:8082/tasks/${this.$route.params.id}`)
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

</style>