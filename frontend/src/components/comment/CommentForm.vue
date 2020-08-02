<template>
    <v-layout row class="px-7">
        <v-text-field
                label="Add comment"
                placeholder="Write something"
                v-model="text"
                @keyup.enter="save"
        />
        <v-btn @click="save">
            Add
        </v-btn>
    </v-layout>
</template>

<script>
    import {mapActions} from 'vuex'
    import axios from "axios";

    export default {
        name: 'CommentForm',
        props: ['taskId', 'task'],
        data() {
            return {
                text: ''
            }
        },
        methods: {
            ...mapActions(['addCommentAction']),
            async save() {
                return axios.post(`http://localhost:8082/projects/${this.$route.params.projectId}/tasks/${this.taskId}/comments`, {
                    text: this.text
                })
                    .then((json) => {
                        this.task.comments = [
                            ...this.task.comments,
                            json.data
                        ]
                    }).finally(() => this.text = ''
                    )

            }
        }
    }
</script>

<style scoped>
</style>