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
// Named import
import { jwtDecode } from "jwt-decode";
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
    async getId() {
      try {
        let jwt = localStorage.getItem('jwt');
        const response = await fetch('http://localhost:10088/userPermission/getIdFromToken', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + jwt
          },
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const data = await response.json();
        localStorage.setItem('id',data);
      } catch (error) {
        this.error = 'Login failed';
      }
    },
    async getInform() {
      try {
        let id = localStorage.getItem('id');
        const response = await fetch(`http://localhost:10088/user-info/get-user-by-id?id=${id}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },

        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const data = await response.json();
        localStorage.setItem('userInform', JSON.stringify(data));
      } catch (error) {
        this.error = 'Login failed';
      }
    },
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
        await this.getId();
        await this.getInform();
        this.error = null;
        this.isLoggedIn = true; // 登录成功后设置为true
        this.startHeartbeat(); // 登录成功后开始发送心跳
      } catch (error) {
        this.error = 'Login failed';
      }
    },
    startHeartbeat() {
      // 每隔5分钟发送一次心跳
      this.heartbeatInterval = setInterval(() => {
        this.sendHeartbeat();
      }, 5 * 1000);
    },

// 发送心跳
    async sendHeartbeat() {
      try {
        const response = await fetch('http://localhost:10088/userPermission/heartbeat', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('jwt'), // 从localStorage中获取jwt
          },
        });

        if (!response.ok) {
          throw new Error('Heartbeat failed');
        }

        console.log('Heartbeat sent');
      } catch (error) {
        console.log('Failed to send heartbeat: ', error);
      }
    },
  },
};
</script>