<template>
  <div class="wrapper">
    <ChatList v-if="chats" :chats="chats" @select-chat="selectChat"/>
    <ChatComponent v-if="selectChatObject" :chat="selectChatObject" />
  </div>
</template>

<script>
import ChatList from './ChatList.vue'
import ChatComponent from './ChatComponent.vue'
import {openDB} from 'idb';

let chats=null;

export default {
  name: 'ChatWrapper',
  components: {
    ChatList,
    ChatComponent
  },
  methods: {
    selectChat(id) {
      // 选中聊天的逻辑，例如显示聊天内容
      this.selectChatId = id;
      this.selectChatObject = this.chats.find(chat => chat.id === id);
      console.log('Selected chat:', id);
    },
  },
  data() {
    return {
      chats: chats,
      selectChatObject: null,
      selectChatId: null
    }
  },
  async created() {
    const db = await openDB('my-db', 1, {
      upgrade(db) {
        db.createObjectStore('chats');
      },
    });
    const chats = await db.getAll('chats');
    if (chats.length > 0) {
      this.firstChat = chats[0];
    } else {
      // 假设的数据
      this.chats = [
        // 示例数据，实际应用中应从服务器获取
        {
          id: 1,
          avatar: 'avatar1.png',
          name: 'User1',
          lastMessage: 'Hello',
          pinned: true,
          lastChatTime: '2022-01-01T00:00:00Z',
          messages: [
            {
              id: 1,
              userid:1,
              avatar: 'avatar1.png',
              name: 'User1',
              text: 'Hello',
              isSelf: false},
            {
              id: 2,
              userid:1,
              avatar: 'avatar2.png',
              name: 'User2',
              text: 'Hi',
              isSelf: true},
          ]
        },
        {

          id: 2,
          avatar: 'avatar1.png',
          name: 'User1',
          lastMessage: 'Hello',
          pinned: true,
          lastChatTime: '2022-01-01T00:00:00Z',
          messages: [
            {
              id: 1,
              userid:1,
              avatar: 'avatar1.png',
              name: 'User1',
              text: 'Hello',
              isSelf: false},
            {
              id: 2,
              userid:1,
              avatar: 'avatar2.png',
              name: 'User2',
              text: 'Hi',
              isSelf: true},
          ]
        },
      ]
    }
  }
}
</script>

<style scoped>
.wrapper {
  display: flex;
  width: 100%;
  height: 100%;
}
.chat-list {
  flex: 0.382;
  height: 100%;
}

.chat {
  flex: 0.618;
  height: 100%;
}
</style>