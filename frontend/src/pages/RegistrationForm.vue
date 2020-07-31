<template>
    <v-container>
        <v-layout class="justify-space-between row">

            <router-link :to="`/signIn`">
                <v-btn class="py-8 px-0" rounded text>
                    <v-icon class="" color="grey" size="40px">
                        arrow_back_ios
                    </v-icon>
                </v-btn>
            </router-link>

            <v-flex class="d-flex justify-center">
                <v-card-title class="text-lg-center mr-16 mb-4">
                    Sign Up
                </v-card-title>
            </v-flex>
        </v-layout>

        <form class="mx-15 px-15 mt-15">
            <v-flex>
                <v-alert
                        v-model="alert"
                        type="error"
                        dismissible
                >
                    User already exists.
                </v-alert>
            </v-flex>
            <v-text-field
                    v-model="username"
                    :error-messages="usernameErrors"
                    :counter="10"
                    label="Username"
                    required
                    @input="$v.username.$touch()"
                    @blur="$v.username.$touch()"
            ></v-text-field>

            <v-text-field
                    v-model="firstName"
                    :error-messages="firstNameErrors"
                    label="First name"
                    required
                    @input="$v.firstName.$touch()"
                    @blur="$v.firstName.$touch()"
            ></v-text-field>

            <v-text-field
                    v-model="lastName"
                    :error-messages="lastNameErrors"
                    label="Last name"
                    required
                    @input="$v.lastName.$touch()"
                    @blur="$v.lastName.$touch()"
            ></v-text-field>

            <v-text-field
                    v-model="email"
                    :error-messages="emailErrors"
                    label="E-mail"
                    required
                    @input="$v.email.$touch()"
                    @blur="$v.email.$touch()"
            ></v-text-field>

            <v-text-field
                    v-model="password"
                    :error-messages="passwordErrors"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show1 ? 'text' : 'password'"
                    name="input-10-1"
                    label="Password"
                    hint="At least 8 characters"
                    counter
                    @click:append="show1 = !show1"
                    required
                    @input="$v.password.$touch()"
                    @blur="$v.password.$touch()"
            ></v-text-field>
            <v-select
                    v-model="select"
                    :items="items"
                    :error-messages="selectErrors"
                    label="Role"
                    required
                    @change="$v.select.$touch()"
                    @blur="$v.select.$touch()"
            ></v-select>
            <v-btn class="mr-4" @click="submit">submit</v-btn>
            <v-btn @click="clear">clear</v-btn>
        </form>

        <!--        <vue-recaptcha-->
        <!--                ref="recaptcha"-->
        <!--                size="invisible"-->
        <!--                :sitekey="sitekey"-->
        <!--                @verify="register"-->
        <!--                @expired="onCaptchaExpired"-->
        <!--        />-->
    </v-container>
</template>

<script>
    import {validationMixin} from 'vuelidate'
    import {required, maxLength, email} from 'vuelidate/lib/validators'

    export default {
        mixins: [validationMixin],
        validations: {
            username: {required, maxLength: maxLength(10)},
            firstName: {required},
            lastName: {required},
            password: {required},
            email: {required, email},
            select: {required},
        },
        data() {
            return {
                show1: false,
                username: '',
                firstName: '',
                lastName: '',
                email: '',
                password: '',
                select: null,
                alert: false,
                items: [
                    'DEVELOPER',
                    'MANAGER'
                ],
            }
        },
        computed: {
            usernameErrors() {
                const errors = []
                if (!this.$v.username.$dirty) return errors
                !this.$v.username.maxLength && errors.push('Name must be at most 10 characters long')
                !this.$v.username.required && errors.push('Username is required.')
                return errors
            },
            firstNameErrors() {
                const errors = []
                if (!this.$v.firstName.$dirty) return errors
                !this.$v.firstName.required && errors.push('First name is required.')
                return errors
            },
            lastNameErrors() {
                const errors = []
                if (!this.$v.lastName.$dirty) return errors
                !this.$v.lastName.required && errors.push('Last name is required.')
                return errors
            },
            emailErrors() {
                const errors = []
                if (!this.$v.email.$dirty) return errors
                !this.$v.email.email && errors.push('Must be valid e-mail')
                !this.$v.email.required && errors.push('E-mail is required')
                return errors
            },
            passwordErrors() {
                const errors = []
                if (!this.$v.password.$dirty) return errors
                !this.$v.password.required && errors.push('Password is required.')
                return errors
            },
            selectErrors() {
                const errors = []
                if (!this.$v.select.$dirty) return errors
                !this.$v.select.required && errors.push('Item is required')
                return errors
            }
        },

        methods: {
            submit() {
                this.$v.$touch()
                if (!this.$v.$invalid) {
                    this.$axios.post('http://localhost:8082/registration', {
                        username: this.username,
                        firstName: this.firstName,
                        lastName: this.lastName,
                        email: this.email,
                        password: this.password,
                        role: this.select
                    }).then((json) => {
                        if (json.data) {
                            this.$router.push('/email/confirmation')
                        } else {
                            this.alert = true
                        }
                    })
                }
            },
            clear() {
                this.$v.$reset()
                this.username = ''
                this.firstName = ''
                this.lastName = ''
                this.email = ''
                this.password = ''
                this.select = null
            },
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
</style>