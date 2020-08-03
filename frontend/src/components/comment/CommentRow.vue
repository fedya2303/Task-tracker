<template>
    <!--    <v-card class="my-2">-->
    <!--            <v-card-title class="ml-2 pb-2 mb-1">-->
    <!--&lt;!&ndash;                {{comment.text}}&ndash;&gt;-->
    <!--                {{comment.author.username}}-->
    <!--            </v-card-title>-->
    <!--        <v-list-item-content class="ml-3 pt-2 mb-2">-->
    <!--            <v-list-item-title>{{comment.text}}</v-list-item-title>-->
    <!--        </v-list-item-content>-->
    <!--    </v-card>-->
    <v-list-item>
        <v-card-title>
            {{comment.author.username}}
        </v-card-title>
        <v-list-item-content>
            <v-list-item-title>{{comment.text}}</v-list-item-title>
        </v-list-item-content>
    </v-list-item>
</template>

<script>
    export default {
        name: "CommentRow",
        props: ['comment']
    }
</script>

<style scoped>

</style>