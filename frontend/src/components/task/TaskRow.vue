<template>
    <v-card class="my-2">
        <v-card-title class="justify-center pt-4 pb-0 ml-2">
            {{task.name}}
        </v-card-title>
        <v-layout row justify-space-between>

            <router-link
                    class="ml-4"
                    :to="{ name: 'Task', params:
                     { projectId: this.$route.params.projectId,
                     taskId: this.task.id} }"
            >
                <div>
                    <v-btn text large>Show</v-btn>
                </div>
            </router-link>
            <router-link
                    class="mr-2"
                    :to="{ name: 'TaskForm', params:
                     { isTaskRow: true, projectId: this.$route.params.projectId,
                     taskId: this.task.id} }"
            >
                <div>
                    <v-btn text large>Edit</v-btn>
                </div>
            </router-link>
            <v-btn class="mt-2" icon @click="del" small>
                <v-icon>delete</v-icon>
            </v-btn>
            <v-flex class="d-flex justify-end mr-6 mt-2">

                {{task.status}}
            </v-flex>
        </v-layout>

    </v-card>
</template>

<script>

    export default {
        name: "TaskRow",
        props: ['task'],
        methods: {
            showTask() {
                this.$router.push({path: `/project/${this.$route.params.projectId}/task/${this.task.id}`})
            },
            editTask() {
                this.$router.push(`/project/${this.$route.params.projectId}/newtask/${this.task.id}`)
            },
            del() {
                this.$store.dispatch('deleteTask', this.task.id)
            }
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
</style>