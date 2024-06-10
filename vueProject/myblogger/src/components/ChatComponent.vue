<template>
  <div class="chat">
    <div class="header">{{ chatName }}</div>
    <div class="messages">
      <MessageComponent
          v-for="message in messages"
          :key="message.id"
          :avatar="message.avatar"
          :name="message.name"
          :text="message.message"
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
  created() {
    // 在组件创建后启动轮询
    this.pollingInterval = setInterval(this.fetchNewMessages, 5000); // 每5秒轮询一次
  },
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
  },
  methods: {
    async sendMessage() {
      let jwt = localStorage.getItem('jwt');
      let user = localStorage.getItem('userInform')
      let message = {
        time: Date.now(),
        userId: localStorage.getItem('id'),
        avatar: localStorage.getItem(user.avatar),
        name: localStorage.getItem(user.username),
        targetId: this.chat.id,
        message: this.inputText,
        type: this.chat.type,
      };
      try {
        const response = await fetch('http://localhost:10088/message/send', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + jwt
          },
          body: JSON.stringify(message),
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        let data = await response.json();
        this.inputText = '';
        this.error = null;
        message.isSelf = true;
        message.messageId= data;
        let messagesSend = [];
        messagesSend.push(message);
        this.$emit('new-message', messagesSend);
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

    async fetchNewMessages() {
      let jwt = localStorage.getItem('jwt');
      const response = await fetch('http://localhost:10088/message/receive', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + jwt
        },
        body: JSON.stringify({
          type:this.chat.type,
          targetId:  localStorage.getItem('id'),
        }),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      this.$emit('new-message', data);
      console.log(data);
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