<template>
  <div class="chat-item">
    <img :src="avatar" alt="User Avatar" class="avatar" />
    <div class="info">
      <div class="name">{{ name }}</div>
      <div class="last-message">{{ lastMessage }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['avatar', 'name', 'lastMessage']
}
</script>

<style scoped>
.chat-item {
  display: flex;
  align-items: center;
  padding: 1em;
  border-bottom: 1px solid #eee;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.info {
  margin-left: 1em;
}

.name {
  font-weight: bold;
}

.last-message {
  margin-top: 0.5em;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>