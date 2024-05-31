<template>
  <div class="message" :class="{ 'right': isSelf }">
    <img :src="avatar" alt="User Avatar" class="avatar" />
    <div class="content">
      <div class="name">{{ name }}</div>
      <div class="text">{{ text }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['avatar', 'name', 'text', 'isSelf']
}
</script>

<style scoped>
.message {
  display: flex;
  margin-bottom: 1em;
}

.message.right {
  flex-direction: row-reverse;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.content {
  margin-left: 1em;
}

.name {
  font-weight: bold;
}

.text {
  margin-top: 0.5em;
}
</style>