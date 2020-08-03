<template>
    <v-container>
        <v-layout class="justify-space-between row">
            <router-link v-if="isTaskRow"
                         :to="`/project/${projectId}`">
                <v-btn class="py-8 px-0" rounded text>
                    <v-icon class="" color="grey" size="40px">
                        arrow_back_ios
                    </v-icon>
                </v-btn>
            </router-link>
            <router-link v-else-if="this.$route.params.taskId"
                         :to="`/project/${projectId}/task/${this.$route.params.taskId}`">
                <v-btn class="py-8 px-0" rounded text>
                    <v-icon class="" color="grey" size="40px">
                        arrow_back_ios
                    </v-icon>
                </v-btn>
            </router-link>

            <router-link v-else :to="`/project/${projectId}`">
                <v-btn class="py-8 px-0" rounded text>
                    <v-icon class="" color="grey" size="40px">
                        arrow_back_ios
                    </v-icon>
                </v-btn>
            </router-link>

            <v-flex class="d-flex justify-center">
                <div class="text-lg-center">
                    <v-card-title>Create new task</v-card-title>
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
        props: ['projectId','isTaskRow'],
        mixins: [validationMixin],
        validations: {
            name: {required, maxLength: maxLength(10)},
            select: {required},
            description: {required}
        },
        data() {
            return {
                task: {},
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
            async loadTask() {
                if (this.$route.params.taskId) {
                    axios.get(`http://localhost:8082/projects/${this.$route.params.projectId}/tasks/${this.$route.params.taskId}`)
                        .then((json) => {
                            this.task = json.data;
                            this.name = this.task.name;
                            this.description = this.task.description;
                            this.select = this.task.status;
                        })
                }
            },
            submit() {
                this.$v.$touch()
                if (!this.$v.$invalid) {
                    if (this.$route.params.taskId) {
                        this.$store.dispatch('updateTask', {
                            projectId: this.$route.params.projectId,
                            id: this.$route.params.taskId,
                            name: this.name,
                            description: this.description,
                            status: this.select
                        }).then(() => {
                            this.$router.push(`/project/${this.$route.params.projectId}/task/${this.$route.params.taskId}`)
                        })
                    } else {
                        this.$store.dispatch('createTask', {
                            id: this.projectId,
                            name: this.name,
                            description: this.description,
                            status: this.select
                        }).then(() => {
                            this.$router.push({path: `/project/${this.projectId}`})
                        })
                    }
                }
            },
            clear() {
                this.$v.$reset()
                this.name = ''
                this.description = ''
                this.select = null
            }
        },
        created() {
            this.loadTask()
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
</style>