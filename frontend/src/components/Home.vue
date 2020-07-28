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
  }
};
</script>

<style scoped>
  a {
    color: #42b983;
  }
</style>
