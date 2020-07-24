<template>
    <v-container>
        <v-layout class="justify-space-between row">
            <router-link :to="`/main`">
                <v-btn class="py-8 px-0" rounded text>
                    <v-icon class="" color="grey" size="40px">
                        arrow_back_ios
                    </v-icon>
                </v-btn>
            </router-link>

            <v-flex class="d-flex justify-center">
                <div class="text-lg-center" v-if="this.$route.params.id">
                    <v-card-title primary-title class="layout justify-center">Update project</v-card-title>
                    <h2>{{project.name}}</h2>
                </div>
                <div v-else class="text-lg-center">
                    <v-card-title>Create new project</v-card-title>
                </div>
            </v-flex>
        </v-layout>
        <form class="mx-15 px-15 mt-15">
            <v-text-field
                    v-model="name"
                    :error-messages="nameErrors"
                    :counter="10"
                    label="Name"
                    required
                    @input="$v.name.$touch()"
                    @blur="$v.name.$touch()"
            ></v-text-field>
            <v-textarea
                    v-model="description"
                    :error-messages="descriptionErrors"
                    required
                    @input="$v.description.$touch()"
                    @blur="$v.description.$touch()"
                    color="teal"
            >
                <template v-slot:label>
                    <div>
                        Description
                    </div>
                </template>
            </v-textarea>
            <v-select
                    v-model="select"
                    :items="items"
                    :error-messages="selectErrors"
                    label="Item"
                    required
                    @change="$v.select.$touch()"
                    @blur="$v.select.$touch()"
            ></v-select>

            <v-btn class="mr-4" @click="submit">submit</v-btn>
            <v-btn @click="clear">clear</v-btn>
        </form>
    </v-container>
</template>

<script>
    import {validationMixin} from 'vuelidate'
    import {required, maxLength} from 'vuelidate/lib/validators'
    import axios from "axios";

    export default {
        mixins: [validationMixin],
        validations: {
            name: {required, maxLength: maxLength(10)},
            select: {required},
            description: {required}
        },
        data() {
            return {
                project: {},
                name: '',
                description: '',
                select: null,
                items: [
                    'WAITING',
                    'IMPLEMENTATION',
                    'VERIFYING',
                    'RELEASING',
                ],
            }
        },

        computed: {
            selectErrors() {
                const errors = []
                if (!this.$v.select.$dirty) return errors
                !this.$v.select.required && errors.push('Item is required')
                return errors
            },
            nameErrors() {
                const errors = []
                if (!this.$v.name.$dirty) return errors
                !this.$v.name.maxLength && errors.push('Name must be at most 10 characters long')
                !this.$v.name.required && errors.push('Name is required.')
                return errors
            },
            descriptionErrors() {
                const errors = []
                if (!this.$v.description.$dirty) return errors
                !this.$v.description.required && errors.push('Name is required.')
                return errors
            }
        },

        methods: {
            async loadProject() {
                if (this.$route.params.id) {
                    axios.get(`http://localhost:8082/projects/${this.$route.params.id}`)
                        .then((json) => {
                            this.project = json.data;
                            this.name = this.project.name;
                            this.description = this.project.description;
                            this.select = this.project.status;
                        })
                }
            },
            submit() {
                this.$v.$touch()
                if (!this.$v.$invalid) {
                    if (this.$route.params.id) {
                        this.$store.dispatch('updateProject', {
                            id: this.project.id,
                            name: this.name,
                            description: this.description,
                            status: this.select
                        }).then(() =>
                            this.$router.push({path: `/project/${this.$route.params.id}`})
                        )
                    } else {
                        this.$store.dispatch('createProject', {
                            name: this.name,
                            description: this.description,
                            status: this.select
                        }).then(() => {
                            const id = this.$store.getters.getProjects[this.$store.getters.getProjects.length - 1].id
                            this.$router.push({path: `/project/${id}`})
                        })
                    }
                }
            },
            clear() {
                this.$v.$reset()
                this.name = ''
                this.description = ''
                this.select = null
            },
        },
        beforeMount() {
            this.loadProject();
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
</style>