<template>
  <div class="text-center">
    <h1 class="mt-10">Home page</h1>
    <h2>{{ data }}</h2>

    <h2>
      <router-link
        to="/main"
        color="black"
      >
        Go to main page
      </router-link>
    </h2>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      data: '',
      status: '',
    };
  },
  created() {
    this.getHomePageInformation();
  },
  methods: {
    getHomePageInformation() {
      this.$axios
        .get('http://localhost:8082/homepage')
        .then((response) => {
          console.log('Get response: ', response.data);
          this.data = response.data;
        })
        .catch((error) => {
          this.alert = true;
          console.error(error);
        });
    },
  },
};
</script>

<style scoped>
  a {
    color: #42b983;
  }
</style>
