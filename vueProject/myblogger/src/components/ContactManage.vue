<template>
  <div class="contact-manage">
    <div class="search">
      <input type="text" v-model="searchText" placeholder="Search...">
      <button @click="search">Search</button>
    </div>
    <FriendGroupList v-if="groups || friends"
                     :friends="friends"
                     :groups="groups"
                     :friendsClickName="friendsClickName"
                     :groupClickName="groupClickName"
                     @click-person="ClickPerson"
                     @click-group="ClickGroup"/>
  </div>
</template>

<script>
import FriendGroupList from "@/components/FriendGroupList.vue";

export default {
  components: {
    FriendGroupList
  },
  data() {
    return {
      selectedList: 'friends',
      searchText: '',
      friendsClickName: '确认添加',
      groupClickName: '同意加入群聊',
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
  methods: {
    search() {
      console.log('Searching for:', this.searchText);
    },
    ClickPerson() {
      console.log('Clicked Person');
    },
    ClickGroup() {
      console.log('Clicked Group');
    }
  }
}
</script>