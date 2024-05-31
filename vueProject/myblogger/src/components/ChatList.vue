<template>
  <div class="chat-list">
    <ChatItem
        v-for="chat in sortedChats"
        :key="chat.id"
        :avatar="chat.avatar"
        :name="chat.name"
        :lastMessage="chat.lastMessage"
        @mouseover="highlightChat(chat.id)"
        @mouseleave="unhighlightChat(chat.id)"
        @click="selectChat(chat.id)"
        :class="{ 'highlight': chat.id === highlightedChatId }"
    />
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