<template>
  <div class="relation-wrapper">
    <SearchAndAdd />
    <RelationShip  />
    <ContactManage />
  </div>
</template>

<script setup>
import RelationShip from "@/components/RelationShip.vue";
import SearchAndAdd from "@/components/SearchAndAdd.vue";
import ContactManage from "@/components/ContactManage.vue";
</script>

<style scoped>
.relation-wrapper {
  display: flex;
}
</style>