<template>
    <v-container>
        <v-layout align-space-around justify-start column>
            <task-row  v-for="task in tasks"
                      :task="task"
                      :key="task.id"
            ></task-row>
        </v-layout>
    </v-container>
</template>

<script>
    import TaskRow from "./TaskRow";

    export default {
        name: "TaskList",
        components: {TaskRow},
        props: ['tasks']
    }
</script>

<style scoped>

</style>