
<template>
  <div class="sidebar">
    <router-link to="/chat">Chat
      <div class="icon">Chat Icon</div>
    </router-link>
    <router-link to="/relation">Relation
      <div class="icon">Relationships Icon</div>
    </router-link>
    <!-- 添加更多的导航链接 -->
  </div>
</template>

<script>
export default {
  name: 'Sidebar'
}
</script>

<style scoped>
</style>