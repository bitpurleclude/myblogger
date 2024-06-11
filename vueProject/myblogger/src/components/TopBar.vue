<template>
  <div class="top-bar">
    <div class="user-info">
      <img :src="displayAvatar" alt="User Avatar">
      <span>{{ displayName }}</span>
      <button v-if="user.isLoggedIn" @click="logout">退出登录</button>
      <button v-else @click="login">登录</button>
    </div>
    <login v-if="loginFormVisible" @close="loginFormVisible = false" @login-s="loginS"></login>
  </div>
</template>

<script>
import login from './login.vue';

export default {
  components: {
    login
  },
  data() {
    return {
      user: {
        isLoggedIn: false,
        name: '',
        avatar: ''
      },
      loginFormVisible: false
    }
  },
  computed: {
    displayName() {
      return this.user.isLoggedIn ? localStorage.getItem('userInform').username : '游客'   ;
    },
    displayAvatar() {
      return this.user.isLoggedIn ? localStorage.getItem('userInform').avatar : '游客头像的URL';
    }
  },
  methods: {
    logout() {
      // 这里是退出登录的代码
    },
    login() {
      this.loginFormVisible = true;
    },
    loginS() {
      const token = localStorage.getItem('jwt');
    }
  },
}
</script>

<style scoped>
.top-bar {
  position: fixed;
  top: 0;
  width: 100%;
  border-bottom: 1px solid #ccc; /* 分界线 */
  z-index: 1000; /* 确保top-bar始终在其他元素之上 */
  /* 其他样式 */
}

.user-info {
  display: flex;
  justify-content: flex-end;
  /* 你的样式代码 */
}
</style>