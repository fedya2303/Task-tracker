<template>
    <v-container>
        <v-layout align-space-around justify-start column>
            <div v-if="comments && comments.length">
            <comment-row v-for="comment in comments"
                         :comment="comment"
                         :key="comment.id"
            >
            </comment-row>
            </div>
            <div v-else>Empty list.</div>
        </v-layout>
    </v-container>
</template>

<script>
    import CommentRow from "./CommentRow.vue";
    export default {
        name: "CommentList",
        components: {CommentRow},
        props: ['comments']
    }
</script>

<style scoped>

</style>