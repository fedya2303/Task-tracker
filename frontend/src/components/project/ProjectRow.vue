<template>
    <v-card class="my-2">
        <v-layout row justify-space-between>
            <v-card-title class="ml-2">
                {{project.name}}
            </v-card-title>
            <v-flex class="d-flex justify-end mr-5 mt-2">
                {{project.status}}
            </v-flex>
        </v-layout>
        <v-btn
                @click="showProject"
                text
        >
            Show
        </v-btn>
    </v-card>
</template>

<script>
    export default {
        name: "ProjectRow",
        props: ['project'],
        methods: {
            showProject() {
                const id = this.project.id
                this.$router.push({path: `/project/${id}`})
            }
        }
    }
</script>

<style scoped>

</style>