<template>
  <div class="friend-group-list">
    <select v-model="selectedList">
      <option value="friends">朋友</option>
      <option value="groups">群组</option>
    </select>

    <input type="range" min="0" :max="maxScroll" v-model="scrollPosition">

    <ul v-if="friends !=null && selectedList === 'friends' " ref="friendList">
      <li v-for="friend in sortedFriends" :key="friend.id">
        <img :src="friend.avatar" alt="朋友头像">
        {{ friend.name }}
        <button @click="removeFriend(friend.id)">{{ friendsClickName }}</button>
      </li>
    </ul>

    <ul v-if="groups !=null &&selectedList === 'groups'" ref="groupList">
      <li v-for="group in sortedGroups" :key="group.id">
        <img :src="group.avatar" alt="群组头像">
        {{ group.name }}
        <button @click="leaveGroup(group.id)">{{ groupClickName }}</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    friends: {
      type: Array,
      default: () => []
    },
    groups: {
      type: Array,
      default: () => []
    },
    friendsClickName: {
      type: String,
      default: '移除朋友'
    },
    groupClickName: {
      type: String,
      default: '退出群聊'
    },
  },
  data() {
    return {
      selectedList: 'friends',
      scrollPosition: 0,
    }
  },
  computed: {
    sortedFriends() {
      return this.friends.sort((a, b) => a.name.localeCompare(b.name));
    },
    sortedGroups() {
      return this.groups.sort((a, b) => a.name.localeCompare(b.name));
    },
      maxScroll() {
        const list = this.selectedList === 'friends' ? this.$refs.friendList : this.$refs.groupList;
        return list ? list.scrollHeight - list.clientHeight : 0;
      }
  },
  watch: {
    scrollPosition(newVal) {
      const list = this.selectedList === 'friends' ? this.$refs.friendList : this.$refs.groupList;
      if (list) {
        list.scrollTop = newVal;
      }
    }
  },
  methods: {
    removeFriend(id) {
      // 取消朋友关系的逻辑
      this.friends = this.friends.filter(friend => friend.id !== id);
    },
    leaveGroup(id) {
      // 退出群聊的逻辑
      this.groups = this.groups.filter(group => group.id !== id);
    }
  }
}
</script>
<style scoped>
.friend-group-list {
  border: 1px solid #000; /* 设置边框颜色和宽度 */
  padding: 10px; /* 设置内边距 */
  margin: 10px; /* 设置外边距 */
}

.friend-group-list ul {
  list-style: none; /* 移除列表样式 */
  padding: 0; /* 移除内边距 */
}

.friend-group-list li {
  border: 1px solid #000; /* 设置边框颜色和宽度 */
  padding: 10px; /* 设置内边距 */
  margin-bottom: 10px; /* 设置下方外边距 */
}
</style>