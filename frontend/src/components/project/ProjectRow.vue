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
        <v-btn v-if="isManager"
                @click="updateProject"
                text>
            Edit
        </v-btn>
    </v-card>
</template>

<script>
    import {mapGetters} from "vuex";

    export default {
        name: "ProjectRow",
        props: ['project'],
        computed: {...mapGetters(['getProjects', 'getUser']),
            isManager: function () {
                if (this.getUser) {
                    return this.getUser.roles.some(role => role === 'MANAGER')
                }
                return false
            }
        },
        methods: {
            showProject() {
                const id = this.project.id
                this.$router.push({path: `/project/${id}`})
            },
            updateProject() {
                const id = this.project.id
                this.$router.push({path: `/newproject/${id}`})
            }
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
</style>