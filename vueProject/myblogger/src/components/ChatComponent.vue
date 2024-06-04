<template>
  <div class="chat">
    <div class="header">{{ chatName }}</div>
    <div class="messages">
      <MessageComponent
          v-for="message in messages"
          :key="message.id"
          :avatar="message.avatar"
          :name="message.name"
          :text="message.text"
          :isSelf="message.isSelf"
      />
    </div>
    <div class="input-area">
      <input v-model="inputText" type="text" placeholder="Type a message"/>
      <button @click="sendMessage">Send</button>
    </div>
  </div>
</template>

<script>
import MessageComponent from './MessageComponent.vue'

export default {
  components: {
    MessageComponent
  },
  props: ['chat'],
  data() {
    return {
      chatName: this.chat.name,
      inputText: '',
      messages: this.chat.messages,
    }
  },
  computed: {
    chatName() {
      return this.chat.name;
    },
    messages() {
      return this.chat.messages;
    }
  },
  methods: {
    async sendMessage() {
      let jwt = localStorage.getItem('jwt');
      try {
        const response = await fetch('http://localhost:10088/message/send', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + jwt
          },
          body: JSON.stringify({
            userId: 1,
            targetId: 2,
            message: "asdhklj",
            type: "PRIVATE",
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
    pollMessages() {
      // 轮询消息的逻辑，例如调用API获取新消息
      console.log('Polling for new messages...');
      // 假设你有一个方法 fetchNewMessages 用于获取新消息
      let newMessage = this.fetchNewMessages();
      if (newMessage) {
        this.messages.push(newMessage); // 将新的消息添加到 messages 数组中
        this.$emit('new-message', newMessage);
      }
    },
    created() {
      // 在组件创建后启动轮询
      this.pollingInterval = setInterval(this.pollMessages, 5000); // 每5秒轮询一次
    },
    fetchNewMessages() {
      // 这里是获取新消息的逻辑，你需要根据你的应用实际情况来实现这个方法
      // 这个方法应该返回新的消息，如果没有新消息，则返回 null 或 undefined
    },

    beforeDestroy() {
      // 在组件销毁前停止轮询
      clearInterval(this.pollingInterval);
    },
  },
}
</script>

<style scoped>
.chat {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.header {
  height: 50px;
  line-height: 50px;
  text-align: center;
  background-color: #f0f0f0;
}

.messages {
  flex-grow: 1;
  overflow-y: auto;
  padding: 1em;
}

.input-area {
  display: flex;
  padding: 1em;
}

.input-area input {
  flex-grow: 1;
  margin-right: 1em;
}
</style>