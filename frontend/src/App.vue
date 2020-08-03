<template>
  <v-app>
    <v-app-bar
            app
            color="teal lighten-1"
            dark
    >
      <v-toolbar-title>
        <router-link
                to="/home"
                tag="span"
                style="cursor: pointer"
        >
          Task-tracker
        </router-link>
      </v-toolbar-title>
      <v-spacer />
      <v-toolbar-items class="hidden-xs-only">
        <v-btn
                v-for="item in menuItems"
                :key="item.title"
                text
                :to="item.path"
        >
          <v-icon left>
            {{ item.icon }}
          </v-icon>
          {{ item.title }}
        </v-btn>
        <v-btn
                v-if="isAuthenticated"
                text
                @click="userSignOut"
        >
          <v-icon left>
            exit_to_app
          </v-icon>
          Sign Out
        </v-btn>
      </v-toolbar-items>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
  import EventBus from './event-bus';
  export default {
    name: 'App',
    data() {
      return {
        isAuthenticated: false,
      };
    },
    computed: {
      menuItems() {
        if (this.isAuthenticated) {
          return [
            { title: 'Home', path: '/home', icon: 'home' },
            { title: 'Projects', path: '/main', icon: 'vpn_key' },
          ];
        }
        return [
          { title: 'Home', path: '/home', icon: 'home' },
          { title: 'Sign In', path: '/signIn', icon: 'lock_open' },
        ];
      },
    },
    created() {
      this.isAuthenticated = localStorage.getItem('auth');
      this.$store.dispatch('userCreds');
      // Use localstorage because isAuthenticated from $store is undefined when event is called
      EventBus.$on('authenticated', () => {
        this.isAuthenticated = localStorage.getItem('auth');
      });
    },
    beforeDestroy() {
      EventBus.$off('authenticated');
    },
    methods: {
      userSignOut() {
        this.$store.dispatch('userSignOut');
      },
    },
  };
</script>


<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>