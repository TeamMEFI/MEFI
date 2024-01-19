<template>
  <div>
    <h1>Home</h1>
    <header><HeaderVue></HeaderVue></header>
    <main>
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
</style>