<template>
  <div id="app">
    <div class="app-container">
      <TopBar />
      <Sidebar />
      <router-view/>
<!--      <RelationWrapper />-->
<!--      <ChatWrapper />-->
<!--      <FriendGroupList />-->
<!--      <SearchAndAdd />-->
<!--      <RelationShip />-->
<!--      <ContactManage />-->
<!--      <ChatList />-->
<!--      <ChatComponent />-->
    </div>
  </div>
</template>

<script>
import Sidebar from './components/Sidebar.vue'
import ChatWrapper from "@/components/ChatWrapper.vue";
import FriendGroupList from "@/components/FriendGroupList.vue";
import SearchAndAdd from "@/components/SearchAndAdd.vue";
import RelationShip from "@/components/RelationShip.vue";
import ContactManage from "@/components/ContactManage.vue";
import RelationWrapper from "@/components/RelationWrapper.vue";
import TopBar from "@/components/TopBar.vue";

export default {
  name: 'App',
  components: {
    RelationWrapper,
    ContactManage,
    Sidebar,
    ChatWrapper,
    FriendGroupList,
    SearchAndAdd,
    RelationShip,
    TopBar,
    // ChatList,
    // ChatComponent
  }
}
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
}
body {
  padding-top: 50px; /* 这个值应该等于你的top-bar的高度 */
}
.app-container {
  display: flex;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
.wrapper{
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