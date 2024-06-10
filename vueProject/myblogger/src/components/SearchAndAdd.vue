<template>
  <div class="search-and-add">
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
      friendsClickName: '添加朋友',
      groupClickName: '加入群聊',
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
    async search() {
      console.log('Searching for:', this.searchText);
      try {
        const response = await fetch(`http://localhost:10088/user-info/get-user-by-id?id=${this.searchText}`);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const userInfo = await response.json();
        let friend={
          id: userInfo.id,
          name: userInfo.username,
          avatar: userInfo.avatar
        }
        this.friends=[];
        this.friends.push(friend);
      } catch (error) {
        console.error('Error fetching user info:', error);
      }
    },
    async ClickPerson(id) {
      try {
        const response = await fetch(`http://localhost:10088/relation/request_Relation`,{
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
    },
    ClickGroup() {
      console.log('Clicked Group');
    }
  }
}
</script>