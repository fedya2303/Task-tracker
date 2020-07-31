<template>
    <v-container>
            <v-card-title mt-5 class="d-flex justify-center">
                Sign In
            </v-card-title>

        <v-flex
                xs12
                sm6
                offset-sm3
                mt-3
        >
            <form @submit.prevent="userSignIn">
                <v-layout column>
                    <v-flex>
                        <v-alert
                                v-model="alert"
                                type="error"
                                dismissible
                        >
                            Wrong input.
                        </v-alert>
                    </v-flex>
                    <v-flex>
                        <v-text-field
                                id="username"
                                v-model="username"
                                name="username"
                                label="Username"
                                type="text"
                                required
                        />
                    </v-flex>
                    <v-flex>
                        <v-text-field
                                id="password"
                                v-model="password"
                                name="password"
                                label="Password"
                                type="password"
                                required
                        />
                    </v-flex>
                    <v-layout>
                        <v-btn
                                type="submit"
                                :disabled="loading"
                                dark
                                color="teal lighten-1"
                                autofocus
                        >
                            Sign In
                        </v-btn>
                        <router-link
                                class="ml-7"
                                :to="`/registration`"
                        >
                            Sign Up
                        </router-link>
                    </v-layout>
                </v-layout>
            </form>
        </v-flex>
    </v-container>
</template>

<script>
    export default {
        data() {
            return {
                username: '',
                password: '',
                alert: false,
            };
        },
        computed: {
            error() {
                return this.$store.getters.getError;
            },
            loading() {
                return this.$store.getters.getLoading;
            },
        },
        watch: {
            error(value) {
                if (value) {
                    this.alert = true;
                }
            },
            alert(value) {
                if (!value) {
                    this.$store.commit('setError', false);
                }
            },
        },
        methods: {
            userSignIn() {
                this.$store.dispatch('userSignIn', {username: this.username, password: this.password});
            },
        },
        created() {
            this.$store.commit('setError', false);
        }
    };
</script>
