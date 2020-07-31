<template>
    <v-container>
        <div class="text-lg-center">
            <div v-if="isActivated">
                <h2>User successfully activated!</h2>
            </div>
            <div v-else>
                <h2>Sorry, mistake</h2>
            </div>
        </div>
    </v-container>
</template>

<script>
    export default {
        name: "EmailActivation",
        data() {
          return {
              isActivated: false
          }
        },
        methods: {
            activateUser() {
                this.$axios.get(`http://localhost:8082/activate/${this.$route.params.code}`)
                    .then((json) => {
                        this.isActivated = json.data
                    })
            }
        },
        created() {
            this.activateUser()
        }
    }
</script>

<style scoped>

</style>