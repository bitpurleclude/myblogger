<template>
  <div class="register">
    <h2>Register</h2>
    <form @submit.prevent="register">
      <div>
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="email" required>
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required>
      </div>
      <button type="submit">Register</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    register() {
      // 这里是注册逻辑，你可能需要调用后端的API
      console.log('Email:', this.email);
      console.log('Password:', this.password);
    }
  }
}
</script>

<style scoped>
.register {
  width: 300px;
  margin: 0 auto;
}

.register form {
  display: flex;
  flex-direction: column;
}

.register form div {
  margin-bottom: 1em;
}

.register form button {
  cursor: pointer;
}
</style>