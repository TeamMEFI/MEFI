<template>
    <v-card class="bgcolor-setting w-100 h-100 rounded-lg">
        <v-card-title class="ma-0 pa-0">
            <v-toolbar>
                <v-toolbar-title class="font-weight-bold text-h5">팀원 수정</v-toolbar-title>
                <v-spacer></v-spacer>
            </v-toolbar>
        </v-card-title>
        <v-card-text>
            <v-container fluid class="h-100 w-100 align-center">
                <v-row class="h-100">
                    <v-col cols="5" class="h-100 ">
                        <div class="h-100 w-100 elevation-1 rounded-lg">
                            <v-toolbar class="rounded-t-lg" height="50">
                                <v-toolbar-title class="font-weight-bold text-h5">팀원 목록</v-toolbar-title>
                            </v-toolbar>
                            <v-list>
                                <v-list-item
                                    v-for="user in members"
                                    :title="user.name + ' / ' + user.email"
                                    :value="user.id"
                                    
                                    @click="excludememberdata = user.id"
                                    >
                                </v-list-item>
                            </v-list>
                        </div>
                    </v-col>
                    <v-col cols="5" class="h-100">
                        <div class="h-100 w-100 elevation-1 rounded-lg">
                            <v-toolbar class="rounded-t-lg" height="50">
                                <v-toolbar-title class="font-weight-bold text-h5">검색 목록</v-toolbar-title>
                                <v-text-field
                                density="compact"
                                    variant="outlined"
                                    v-model="searchName"
                                    append-inner-icon="mdi-magnify"
                                    single-line
                                    hide-details
                                    class="me-3 bg-white"
                                    @click:append-inner="search"
                                    @keyup.enter="search"
                                ></v-text-field>
                            </v-toolbar>
                            <v-list>
                                <v-list-item
                                    v-for="user in searchList"
                                    :title="user.name + ' / ' + user.email"
                                    :value="user.id"
                                    @click="addmemberdata = user.id"
                                    >
                                </v-list-item>
                            </v-list>
                        </div>
                    </v-col>
                    <v-col cols="2" class="d-flex flex-column justify-start align-center h-100">
                        <v-btn width="100" class="ma-3">팀장 위임</v-btn>
                        <v-btn width="100" class="ma-3" @click="addMember">팀 초대</v-btn>
                        <v-btn width="100" class="ma-3" @click="excludeMember">팀 추방</v-btn>
                    </v-col>
                </v-row>
            </v-container>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
            variant="text"
            class="text-h5"
            @click="close"
            >
            Close
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script setup>
import { ref, onMounted, watchEffect } from 'vue';
import { selectTeamMate, addTeamMate, excludeTeamMate } from '@/api/team.js';
import { userSearch } from '@/api/user.js'
import { useUserStore } from "@/stores/user"

const props = defineProps({
  teamId: Number
});

const addmemberdata = ref(null)
const excludememberdata = ref(null)

const emit = defineEmits(['closeDialog'])

const searchList = ref([])
const searchName = ref('')
const members = ref([])
const store = useUserStore()

const search = async () => {
    if (searchName.value === '') return;
    const data = searchName.value
    await userSearch(
        data, (response) => {
            searchList.value = response.data.dataBody.filter(member => {
                return !members.value.some(selectedMember => selectedMember.email === member.email) && member.email !== store.userInfo.email;
            });
        },
        (error) => {
            console.log(error)
        }
    )
}

const selectmember = async () => {
    await selectTeamMate(
        props.teamId,(response) => {
            members.value = response.data.dataBody.filter(member => {
                if (member.email !== store.userInfo.email)
                    return member
            })
        },
        (error)=>{
            console.log(error)
        }
    )
}

const addMember = async () => {
    if (addmemberdata.value === null) return;
    const data = {
        teamid: props.teamId,
        userid: addmemberdata.value
    }
    await addTeamMate(
        data,(response) => {
            selectmember();
            searchList.value = searchList.value.filter(member => member.id !== addmemberdata.value);
            addmemberdata.value = null;
        },
        (error)=>{
            console.log(error)
        }
    )
};

const excludeMember = async () => {
    if (excludememberdata.value === null) return;
    const data = {
        teamid: props.teamId,
        userid: excludememberdata.value
    }
    await excludeTeamMate(
        data,(response) => {
            search()
            members.value = members.value.filter(member => member.id !== excludememberdata.value);
            excludememberdata.value = null
        },
        (error)=>{
            console.log(error)
        }
    )
};

// 최초 생성시 팀원 조회
onMounted(() => {
  selectmember();
});

const close = () => {
    emit('closeDialog')
}
</script>

<style scoped>
.selected {
    background-color: aqua;
}
</style>