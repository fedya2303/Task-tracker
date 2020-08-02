<template>
    <v-container>
        <template
                v-for="(item, index) in comments"
        >
            <v-divider
                    v-if="index > 0"
                    :key="index"
            >
            </v-divider>

            <comment-row
                :comment="item"
                :key="'item' + index"
            >
            </comment-row>
        </template>
        <comment-form :task-id="taskId" :task="task"></comment-form>
<!--        <v-layout align-space-around justify-start column>-->
<!--            <div v-if="comments && comments.length">-->
<!--                <comment-row v-for="comment in comments"-->
<!--                             :comment="comment"-->
<!--                             :key="comment.id"-->
<!--                >-->
<!--                </comment-row>-->
<!--            </div>-->
<!--            <div v-else>Empty list.</div>-->
<!--        </v-layout>-->
    </v-container>
</template>

<script>
    import CommentRow from "./CommentRow.vue"
    import CommentForm from "./CommentForm"

    export default {
        name: "CommentList",
        components: {CommentRow, CommentForm},
        props: ['comments', 'taskId', 'task']
    }
</script>

<style scoped>

</style>