<template>
    <v-container>
        <v-flex class="mb-5 mt-3 text-md-center">
            <h1>Projects</h1>
            <div class=" mt-1">Choose any project</div>
            <div v-if="isManager"> or create
                <router-link :to="`/newproject`">new</router-link>
            </div>
        </v-flex>

        <v-layout align-space-around justify-start column>
            <div v-if="getProjects && getProjects.length">
                <project-row v-for="project in getProjects"
                             :project="project"
                             :key="project.id"
                >
                </project-row>
            </div>
            <div v-else>Empty list.</div>
        </v-layout>
    </v-container>
</template>

<script>
    import ProjectRow from '../components/project/ProjectRow.vue'
    import {mapGetters} from 'vuex'

    export default {
        components: {
            ProjectRow
        },
        name: "ProjectList",
        data() {
            return {
                isM: true,
                user: {}
            }
        },
        computed: {...mapGetters(['getProjects', 'getUser']),
            isManager: function () {
                if (this.getUser && this.getUser.roles) {
                    return this.getUser.roles.some(role => role === 'MANAGER')
                }
                return false
            }
        },
        beforeMount() {
            this.$store.dispatch('loadProjects')
        },
    }
</script>

<style scoped>

</style>