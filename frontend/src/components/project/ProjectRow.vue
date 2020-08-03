<template>
    <v-card class="my-2">

        <v-card-title class="justify-center pb-1">
            {{project.name}}
        </v-card-title>

        <v-layout row justify-space-between>
            <router-link
                    class="ml-4"
                    :to="{ name: 'Project', params: { projectId: this.project.id}}"
            >
                <div>
                    <v-btn text large>Show</v-btn>
                </div>
            </router-link>
            <router-link
                    class="mr-2"
                    :to="{ name: 'ProjectForm', params: { projectId: this.project.id } }"
            >
                <div>
                    <v-btn text large>Edit</v-btn>
                </div>
            </router-link>
            <v-flex class="d-flex justify-end mr-6 mt-2">
                {{project.status}}
            </v-flex>
        </v-layout>
    </v-card>
</template>

<script>
    import {mapGetters} from "vuex";

    export default {
        name: "ProjectRow",
        props: ['project'],
        computed: {
            ...mapGetters(['getProjects', 'getUser']),
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