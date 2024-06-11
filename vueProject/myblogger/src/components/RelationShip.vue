<template>
  <div class="relation-ship">
    <FriendGroupList v-if="groups || friends"
                     :friends="friends"
                     :groups="groups"
                     :friendsClickName="friendsClickName"
                     :groupClickName="groupClickName"
                     @click-person="ClickPerson"
                     @click-group="ClickGroup">
      <template v-slot:friendButton="{ friend }">
        <button @click="friendButtonClick(friend)">开始聊天</button>
      </template>
      <template v-slot:groupButton="{ group }">
        <button @click="groupButtonClick(group)">Click me</button>
      </template>
    </FriendGroupList>
  </div>
</template>

<script>
import FriendGroupList from "@/components/FriendGroupList.vue";
import {openDB} from "idb";

const db = null;
let chats = null;

export default {
  name: 'RelationShip',
  data() {
    return {
      selectedList: 'friends',
      scrollPosition: 0,
      friendsClickName: '移除朋友',
      groupClickName: '退出群聊',
      friends: [
        {id: 1, name: 'Alice', avatar: 'alice.png'},
        {id: 2, name: 'Bob', avatar: 'bob.png'},
        {id: 3, name: 'Charlie', avatar: 'charlie.png'},
        {id: 4, name: 'David', avatar: 'david.png'}
      ],
      groups: [
        {id: 1, name: 'Group A', avatar: 'groupA.png'},
        {id: 2, name: 'Group B', avatar: 'groupB.png'},
        {id: 3, name: 'Group C', avatar: 'groupC.png'},
        {id: 4, name: 'Group D', avatar: 'groupD.png'}
      ]
    }
  },
  components: {
    FriendGroupList
  },
  methods: {
    async ClickPerson(id) {
      try {
        const response = await fetch(`http://localhost:10088/relation/deleteRelation`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            userId: localStorage.getItem('id'),
            relationId: id,
            relationType: 'PRIVATE'
          })
        });

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
      } catch (error) {
        console.error('Error fetching user info:', error);
      }
      console.log('Clicked Person');
    },
    ClickGroup() {
      console.log('Clicked Group');
    },
    async getRelationShip() {
      let id = localStorage.getItem('id');
      const response = await fetch(`http://localhost:10088/relation/getRelation?userId=${id}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },

      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();
      this.friends = [];
      for (const relation of data) {
        let relation1 = await this.getInform(relation.relationId);
        if(relation1.username === null) {
          let friend = {
            id: relation.relationId,
            name: relation1.email,
            avatar: relation1.avatar,
            online: relation1.online
          }
          this.friends.push(friend);
        }else {
          let friend = {
            id: relation.relationId,
            name: relation1.username,
            avatar: relation1.avatar,
            online: relation1.online
          }
          this.friends.push(friend);
        }

      }

      // 获取好友和群组信息
    },
    async getInform(id) {
      const response = await fetch(`http://localhost:10088/user-info/get-user-by-id?id=${id}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },

      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();
      return data;
    },
    async friendButtonClick(friend) {
      let chat = null;
      if (chats === null) {
        chats = [];
        const newChat = {
          id: friend.id,
          avatar: friend.avatar,
          name: friend.name,
          type: 'PRIVATE',
          lastMessage: '',
          pinned: false,
          lastChatTime: '2022-01-01T00:00:00Z',
          messages: []
        }
        chats.push(newChat);
        const tx = this.db.transaction('chats', 'readwrite');
        const store = tx.objectStore('chats');
        await store.put(newChat, newChat.id);
        await tx.done;
        chat = newChat;
        this.$router.push({name: 'ChatWrapper', params: {selectChatObject: chat}});
      } else {
        chat = chats.find(chat => chat.id === friend.id && chat.type === 'PRIVATE');
        if (!chat) {
          const newChat = {
            id: friend.id,
            avatar: friend.avatar,
            name: friend.name,
            type: 'PRIVATE',
            lastMessage: '',
            pinned: false,
            lastChatTime: '2022-01-01T00:00:00Z',
            messages: []
          }
          chats.push(newChat);
          const tx = this.db.transaction('chats', 'readwrite');
          const store = tx.objectStore('chats');
          await store.put(newChat, newChat.id);
          await tx.done;
          chat = newChat;
        }
      }
      this.$router.push({name: 'ChatWrapper', params: {selectChatObject: chat}});
    },
    async groupButtonClick(group) {
      let chat = null;
      if (chats === null) {
        chats = [];
        const newChat = {
          id: group.id,
          avatar: group.avatar,
          name: group.name,
          type: 'GROUP',
          lastMessage: '',
          pinned: false,
          lastChatTime: '2022-01-01T00:00:00Z',
          messages: []
        }
        chats.push(newChat);
        const tx = this.db.transaction('chats', 'readwrite');
        const store = tx.objectStore('chats');
        await store.put(newChat,newChat.id);
        await tx.done;
        chat = newChat;
        this.$router.push({ name: 'ChatWrapper', params: { selectChatObject: chat } });
      }else {
        chat = chats.find(chat => chat.id === group.id && chat.type === 'GROUP');
        if (!chat) {
          const newChat = {
            id: group.id,
            avatar: group.avatar,
            name: group.name,
            type: 'GROUP',
            lastMessage: '',
            pinned: false,
            lastChatTime: '2022-01-01T00:00:00Z',
            messages: []
          }
          chats.push(newChat);
          const tx = this.db.transaction('chats', 'readwrite');
          const store = tx.objectStore('chats');
          await store.put(newChat,newChat.id);
          await tx.done;
          chat = newChat;
        }
      }
      this.$router.push({ name: 'ChatWrapper', params: { selectChatObject: chat } });
    },
    startTimer() {
      // 每隔5秒执行一次getRelationShip方法
      this.timerId = setInterval(this.getRelationShip, 5000);
    },
    stopTimer() {
      // 停止定时器
      if (this.timerId) {
        clearInterval(this.timerId);
        this.timerId = null;
      }
    },
  },
  async created() {
    this.db = await openDB('my-db', 1, {
      upgrade(db) {
        db.createObjectStore('chats');
      },
    });
    this.chats = await this.db.getAll('chats');
    this.startTimer();
  },
  beforeDestroy() {
    // 组件销毁前停止定时器
    this.stopTimer();
  },
}
</script>

<style scoped>

</style>