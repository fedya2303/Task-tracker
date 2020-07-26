<template>
    <v-container>
        <v-layout class="justify-space-between row">
            <router-link class="" :to="`/project/${this.$route.params.projectId}`">
                <v-btn class="py-8 px-0" rounded text>
                    <v-icon class="" color="grey" size="40px">
                        arrow_back_ios
                    </v-icon>
                </v-btn>
            </router-link>

            <v-flex class="d-flex justify-center">
                <div  class="text-lg-center">
                    <v-card-title class="mr-16">Project participants</v-card-title>
                </div>
            </v-flex>
        </v-layout>
        <div></div>

        <v-layout class="justify-space-around mt-5">
            <v-text-field
                    class="mr-2"
                    v-model="search"
                    placeholder="Search users"
                    outlined
            ></v-text-field>
            <v-btn
                    class="py-6 mt-1"
                    @click="submit"
            >Submit
            </v-btn>
        </v-layout>
        <div v-for="user in filteredUsers" :key="user.id">
            <v-card class="mt-3 py-3">
                <v-layout class="justify-center">
                    <v-card-title>{{ user.username}}</v-card-title>
                    <v-checkbox

                            v-model="user.isParticipant"
                    ></v-checkbox>
                </v-layout>
            </v-card>
        </div>
    </v-container>
</template>

<script>

    export default {
        name: "ProjectParticipantForm",
        data() {
            return {
                users: [],
                search: '',
            }
        },
        computed: {
            filteredUsers() {
                return this.users.filter((user) => {
                    return user.username.toLowerCase().match(this.search.toLowerCase());
                })
            }
        },
        methods: {
            submit() {
                let usersId = this.users.filter((user) => user.isParticipant === true).map((user) => user.id)
                usersId = [
                    ...usersId,
                    this.$store.state.user.id
                ]
                this.$axios.post(`http://localhost:8082/projects/${this.$route.params.projectId}/users`, usersId)
                this.$router.push(`/project/${this.$route.params.projectId}`)
            },
            getAllUsers() {
                this.$axios.get('http://localhost:8082/users')
                    .then((json) => {
                        this.users = json.data.filter((user) => user.id !== this.$store.state.user.id)
                        this.filterCheckbox()
                    })
            },
            filterCheckbox() {
                let id = parseInt(this.$route.params.projectId)
                this.users.forEach((user) => {
                    if (user.projects.filter((project) =>
                        project.id === id
                    ).length > 0) {
                        user.isParticipant = true
                    }
                })
            }
        },
        created() {
            this.getAllUsers()
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
</style>