<template>
    <div id="profile-container">
        <div id="body-img"><img id="body-img-img" src="../../assets/sampleImg.PNG" alt=""></div>
        <div id="body-private-canlendar">개인캘린더</div>
        <div id="body-team-canlendar">팀캘린더</div>
    </div>
</template>

<script setup>
</script>

<style scoped>
#profile-container{
    border: 1px solid black;
    display: flex;
    flex-direction: column;
    max-width: 350px;
    min-width: 350px;
}
#body-img-img{
    width: 200px;
    height: 200px;
    border-radius: 50%;
    margin: 10px;
}
</style>