<template>
  <div class="wrapper">
    <ChatList v-if="chats" :chats="chats" @select-chat="selectChat"/>
    <ChatComponent v-if="selectChatObject" :chat="selectChatObject" @new-message="newMessage" />
  </div>
</template>

<script>
import ChatList from './ChatList.vue'
import ChatComponent from './ChatComponent.vue'
import {openDB} from 'idb';

let db = await openDB('my-db', 1, {
  upgrade(db) {
    if (!db.objectStoreNames.contains('chats')) {
      db.createObjectStore('chats');
    }
    if (!db.objectStoreNames.contains('uuids')) {
      db.createObjectStore('uuids');
    }
  },
});
// let request = indexedDB.open("my-db", 1); // 提供一个高于当前数据库版本的版本号
//
// request.onupgradeneeded = function(event) {
//   db = event.target.result;
//   if (!db.objectStoreNames.contains("uuids")) {
//     db.createObjectStore("uuids");
//   }
//   if (!db.objectStoreNames.contains("chats")) {
//     db.createObjectStore("chats");
//   }
// };
//
// request.onsuccess = function(event) {
//   db = event.target.result;
// };
//
// request.onerror = function(event) {
//   console.log("Error opening database:", event.target.errorCode);
// };
let chats = await db.getAll('chats');
export default {
  name: 'ChatWrapper',
  components: {
    ChatList,
    ChatComponent
  },
  methods: {

    async newMessage(messages) {
      for (let message of messages) {
        // 创建一个事务
        if (!db.objectStoreNames.contains("uuids")) {
          db.createObjectStore("uuids");
        }
        let tx = db.transaction('uuids', 'readonly');

        // 获取对象存储
        let store = tx.objectStore('uuids');

        // 查询UUID
        let uuidExists = await store.get(message.messageId);

        // 等待事务完成
        await tx.done;

        // 如果UUID已经存在，那么跳过这个消息
        if (uuidExists) {
          continue;
        }
        let chatIndex = chats.findIndex(chat => chat.id === message.targetId && chat.type === message.type);
        if (chatIndex !== -1) {
          chats[chatIndex].messages.push(message);
          chats[chatIndex].lastMessage = message.message;

          // 创建一个事务
          let tx = db.transaction('chats', 'readwrite');

          // 获取对象存储
          let store = tx.objectStore('chats');

          // 添加数据
          await store.put(chats[chatIndex], chats[chatIndex].id);

          // 等待事务完成
          await tx.done;
        }


        chatIndex = chats.findIndex(chat => chat.id === message.userId && chat.type === message.type);
        if (chatIndex !== -1) {
          chats[chatIndex].messages.push(message);
          chats[chatIndex].lastMessage = message.message;

          // 创建一个事务
          let tx = db.transaction('chats', 'readwrite');

          // 获取对象存储
          let store = tx.objectStore('chats');

          // 添加数据
          await store.put(chats[chatIndex], chats[chatIndex].id);

          // 等待事务完成
          await tx.done;
        }

        // 将新消息的UUID添加到IndexedDB中
        let txuuid = db.transaction('uuids', 'readwrite');
        let storeuuid = txuuid.objectStore('uuids');
        await storeuuid.add({ id: message.messageId }, message.messageId);
        await txuuid.done;
      }
    },
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
    if (chats.length > 0) {
      this.firstChat = chats[0];
    } else {
      // 假设的数据
      // this.chats = [
      //   // 示例数据，实际应用中应从服务器获取
      //   {
      //     id: 1,
      //     avatar: 'avatar1.png',
      //     name: 'User1',
      //     type: 'PRIVATE',
      //     lastMessage: 'Hello',
      //     pinned: true,
      //     lastChatTime: '2022-01-01T00:00:00Z',
      //     messages: [
      //       {
      //         id: 1,
      //         userid:1,
      //         avatar: 'avatar1.png',
      //         name: 'User1',
      //         message: 'Hello',
      //         isSelf: false},
      //       {
      //         id: 2,
      //         userid:1,
      //         avatar: 'avatar2.png',
      //         name: 'User2',
      //         message: 'Hi',
      //         isSelf: true},
      //     ]
      //   },
      //   {
      //
      //     id: 2,
      //     avatar: 'avatar1.png',
      //     name: 'User1',
      //     lastMessage: 'Hello',
      //     pinned: true,
      //     lastChatTime: '2022-01-01T00:00:00Z',
      //     messages: [
      //       {
      //         id: 1,
      //         userid:1,
      //         avatar: 'avatar1.png',
      //         name: 'User1',
      //         message: 'Hello',
      //         isSelf: false},
      //       {
      //         id: 2,
      //         userid:1,
      //         avatar: 'avatar2.png',
      //         name: 'User2',
      //         message: 'Hi',
      //         isSelf: true},
      //     ]
      //   },
      // ]
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