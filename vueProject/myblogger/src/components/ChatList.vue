<template>
  <div class="chat-list">
    <div v-for="chat in sortedChats" :key="chat.id">
      <ChatItem
          :avatar="chat.avatar"
          :name="chat.name"
          :lastMessage="chat.lastMessage"
          @mouseover="highlightChat(chat.id)"
          @mouseleave="unhighlightChat(chat.id)"
          @click="selectChat(chat.id)"
          :class="{ 'highlight': chat.id === highlightedChatId }"
      />
      <button @click="downloadChat(chat)">下载聊天记录</button>
      <button @click="deleteChat(chat.id)">删除聊天记录</button>
    </div>
  </div>
</template>

<script>
import ChatItem from './ChatItem.vue'

export default {
  components: {
    ChatItem
  },
  props: {
    chats: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      sortedChats: [],
      highlightedChatId: null,
    }
  },
  created() {
    this.sortChats();
  },
  methods: {
    async deleteChat(chatId) {
      // 打开数据库
      var openRequest = indexedDB.open("my-db");

      openRequest.onsuccess = function(e) {
        var db = e.target.result;

        // 打开对象存储并删除指定聊天的所有记录
        var transaction = db.transaction("chats", "readwrite");
        var objectStore = transaction.objectStore("chats");
        var getRequest = objectStore.delete(chatId);

        getRequest.onsuccess = function(e) {
          console.log("Chat " + chatId + " deleted from IndexedDB.");
        };
      };
    },
    downloadChat(chat) {
      // 将聊天对象转换为字符串
      var dataString = JSON.stringify(chat);

      // 创建 Blob 对象
      var blob = new Blob([dataString], {type: "text/plain"});

      // 创建 URL 对象
      var url = URL.createObjectURL(blob);

      // 创建隐藏的 <a> 元素并触发 click 事件
      var a = document.createElement("a");
      a.style.display = "none";
      a.href = url;
      a.download = "chat_" + chat.id + ".txt";
      document.body.appendChild(a);
      a.click();

      // 清理
      setTimeout(function() {
        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
      }, 0);
    },
    sortChats() {
      this.sortedChats = this.chats.sort((a, b) => {
        if (a.pinned && !b.pinned) {
          return -1;
        } else if (!a.pinned && b.pinned) {
          return 1;
        } else {
          return new Date(b.lastChatTime) - new Date(a.lastChatTime);
        }
      });
    },
    selectChat(id) {
      // 选中聊天的逻辑，例如显示聊天内容
      this.$emit('select-chat', id);
      console.log('Selected chat:', id);
    },
    highlightChat(id) {
      this.highlightedChatId = id;
    },
    unhighlightChat() {
      this.highlightedChatId = null;
    },
  },
}
</script>

<style scoped>
.chat-list {
  width: 300px;
  border-right: 1px solid #eee;
  overflow-y: auto;
}
.chat-item.highlight {
  /* 高亮显示的样式，例如改变背景色 */
  background-color: red;
}
</style>