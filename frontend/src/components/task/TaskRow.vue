<template>
    <v-card class="my-2">
        <v-layout row justify-space-between>
            <v-card-title class="pt-2 ml-2">
                {{task.name}}
            </v-card-title>

            <v-flex class="d-flex justify-end mr-5 mt-2">

                {{task.status}}
            </v-flex>
        </v-layout>
        <v-btn
                @click="showTask"
                text
        >
            Show
        </v-btn>
        <v-btn icon @click="del" small>
            <v-icon>delete</v-icon>
        </v-btn>
    </v-card>
</template>

<script>

    export default {
        name: "TaskRow",
        props: ['task'],
        methods: {
            showTask() {
                const id = this.task.id
                this.$router.push({path: `/task/${id}`})
            },
            del() {
                this.$store.dispatch('deleteTask', this.task.id)
            }
        }
    }
</script>

<style scoped>

</style>