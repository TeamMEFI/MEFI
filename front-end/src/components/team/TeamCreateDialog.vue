<template>
    <v-card class="bgcolor-setting w-100 h-100">
        <v-card-title class="ma-0 pa-0">
            <v-toolbar color="#2A4770" >
                <v-toolbar-title class="font-weight-bold text-h5">Team Create</v-toolbar-title>
                <v-spacer></v-spacer>
            </v-toolbar>
        </v-card-title>
        <v-card-text>
            <v-container class="h-85">
                <v-row>
                    <v-col cols="5">
                        <v-text-field
                        v-model="teamName"
                        label="Team Name"
                        variant="solo"
                        required
                        ></v-text-field>
                    </v-col>
                    <v-col cols="7">
                        <v-text-field
                        v-model="teamDescription"
                        label="Team Description"
                        variant="solo"
                        required
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row class="h-100">
                    <v-col cols="5" class="h-100">
                        <div class="bg-white h-100 elevation-3">
                            <v-toolbar color="#2A4770" >
                                <v-toolbar-title class="font-weight-bold text-h5">New Join</v-toolbar-title>
                                <v-text-field
                                    density="compact"
                                    v-model="searchName"
                                    variant="solo"
                                    append-inner-icon="mdi-magnify"
                                    single-line
                                    hide-details
                                    @click:append-inner="onClick"
                                    @keyup.enter="onClick"
                                    class="me-3"
                                ></v-text-field>
                            </v-toolbar>
                            <v-list>
                                <v-list-item
                                    v-for="user in searchList"
                                    :value="user.id"
                                    @click="clickUser(user)"
                                    :class="{'selected' : user.isSelect}"
                                    >
                                        <p>{{ user.name + ' / ' + user.email }}</p>
                                </v-list-item>
                            </v-list>
                        </div>
                    </v-col>
                    <v-col cols="2" class="d-flex justify-space-evenly flex-column align-center">
                        <v-btn icon @click="addMember">
                            <v-icon>mdi-account-plus</v-icon>
                        </v-btn>
                        <v-btn icon @click="excludeMember">
                            <v-icon>mdi-account-minus</v-icon>
                        </v-btn>
                    </v-col>
                    <v-col cols="5" class="h-100">
                        <div class="bg-white h-100 elevation-3">
                            <v-toolbar color="#2A4770" >
                                <v-toolbar-title class="font-weight-bold text-h5">Added</v-toolbar-title>
                                <v-spacer></v-spacer>
                            </v-toolbar>
                            <v-list>
                                <v-list-item
                                    v-for="selectuser in selectedUsers"
                                    @click="clickUser(selectuser)"
                                    :class="{'selected' : selectuser.isSelect}"
                                    >
                                        <p>{{ selectuser.name + ' / ' + selectuser.email }}</p>
                                </v-list-item>
                            </v-list>
                        </div>
                    </v-col>
                </v-row>
            </v-container>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
            variant="text"
            class="text-h5"
            @click="emit('closeDialog')"
            >
            Close
            </v-btn>
            <v-btn
            variant="text"
            class="text-h5"
            @click="create"
            >
            Save
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script setup>
import { ref } from 'vue';
import { createTeam } from '@/api/team.js'
import { userSearch } from '@/api/user.js'

const emit = defineEmits(['closeDialog'])
const searchList = ref([])
const selectedUsers = ref([]);
const searchName = ref('')
const leaderId = ref('')
const teamName = ref('')
const teamDescription = ref('')

const create = async () => {
    const data = {
        leaderId : leaderId.value,
        teamName : teamName.value,
        teamDescription : teamDescription.value,
        members : selectedUsers.value.map((user) => user.id),
    }
    await createTeam(
        data,(response) => {
            console.log(response)
            emit('closeDialog')
        },
        (error)=>{
            console.log(error)
        }
    )
  }

const onClick = async () => {
    const data = searchName.value
    await userSearch(
        data, (response) => {
            searchList.value = [];
            searchList.value = response.data.dataBody.map((x) => {
                const newData = {
                    isSelect :false,
                    isMember : false,
                    ...x
                }
                return newData
            })
        },
        (error) => {
            console.log(error)
        }
    )
}

const addMember = () => {
    const newList = searchList.value.filter((user) => {
        if (!user.isSelect) {
            return user; // 선택된 사용자만 반환
        }
    });

    const selected = searchList.value.filter((user) => {
        if (user.isSelect) {
            user.isSelect = false;
            user.isMember = true;
            return user; // 선택된 사용자만 반환
        }
    });

    selectedUsers.value = selectedUsers.value.concat(selected);
    searchList.value = newList

};

const excludeMember = () => {
    const newList = selectedUsers.value.filter((user) => {
        if (!user.isSelect) {
            return user; // 선택된 사용자만 반환
        }
    });
    selectedUsers.value = newList
}

const clickUser = (user) => {
    user.isSelect = !user.isSelect
}



</script>

<style scoped>
.selected {
    background-color: aqua;
}
</style>