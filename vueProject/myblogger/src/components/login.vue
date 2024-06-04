<template>
  <div>
    <form @submit.prevent="login" v-if="!isLoggedIn">
      <input type="text" v-model="username" placeholder="Username">
      <input type="password" v-model="password" placeholder="Password">
      <button type="submit">Login</button>
    </form>
    <p v-if="error">{{ error }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      error: null,
      isLoggedIn: false, // 新增的属性
    };
  },
  methods: {
    async login() {
      try {
        const response = await fetch('http://localhost:10088/userPermission/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: this.username,
            password: this.password,
          }),
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const data = await response.json();
        localStorage.setItem('jwt', data.token);
        console.log(data.token)
        this.error = null;
        this.isLoggedIn = true; // 登录成功后设置为true
      } catch (error) {
        this.error = 'Login failed';
      }
    },
  },
};
</script>