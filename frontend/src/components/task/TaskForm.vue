<template>
    <v-container>
        <v-layout class="justify-space-between row">
            <router-link :to="`/project/${projectId}`">
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

    export default {
        props: ['projectId'],
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
            submit() {
                this.$v.$touch()
                if (!this.$v.$invalid) {
                    this.$store.dispatch('createTask', {
                        id: this.projectId,
                        name: this.name,
                        description: this.description,
                        status: this.select
                    }).then(() => {
                        this.$router.push({path: `/project/${this.projectId}`})
                    })
                }
            },
            clear() {
                this.$v.$reset()
                this.name = ''
                this.description = ''
                this.select = null
            }
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
</style>