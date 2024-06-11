<template>
  <div class="contact-manage">
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
  created() {
    this.pollingInterval = setInterval(this.getRequest, 5000);
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
    async getRequest() {
      let id = localStorage.getItem('id');
      try {
        const response = await fetch(`http://localhost:10088/relation/getRequestRelation?userId=${id}`);

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        this.friends = [];
        for (const item of data) {
          const response = await fetch(`http://localhost:10088/user-info/get-user-by-id?id=${item.userId}`);
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }
          const userInfo = await response.json();
          let friend = {
            id: userInfo.id,
            name: userInfo.username,
            avatar: userInfo.avatar
          }
          this.friends.push(friend);
        }
      } catch (error) {
        console.error('Error fetching user info:', error);
      }
      console.log('Searching for:', this.searchText);
    },
    async ClickPerson(targetId) {
      let id = localStorage.getItem('id');
      try {
        const response = await fetch(`http://localhost:10088/relation//confirmTheRelationship`,{
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            userId: id,
            relationId: targetId,
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
    }
  }
}
</script>