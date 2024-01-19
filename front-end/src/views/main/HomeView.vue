<template>
  <div id="container">
    <header><HeaderVue></HeaderVue></header>
    <main id="container-body">
      <ProfileVue></ProfileVue>
      <MainCalendarVue></MainCalendarVue>
    </main>
    <footer><FooterVue></FooterVue></footer>
  </div>
</template>

<script setup>
import HeaderVue from '../../components/layout/Header.vue';
import FooterVue from '../../components/layout/Footer.vue';
import ProfileVue from '@/components/layout/Profile.vue';
import MainCalendarVue from '@/components/calendar/MainCalendar.vue';
</script>

<style scoped>
#container{
  margin-top: 60px;
}
#container-body{
  min-height: 800px;
  display: grid;
  grid-template-columns: 1.5fr 8fr
}
</style>