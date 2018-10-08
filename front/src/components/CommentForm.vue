<template>
    <form id="commentForm" v-on:submit="sendComment" >
        <div class="form-group text-area">
                        <textarea class="form-control" rows="3" v-model="text" maxlength="10000"
                                  required></textarea>
        </div>

        <input type="hidden" name="userId" value="${currentUserId}">
        <input type="hidden" name="articleId" value="${article.id}">

        <div class="submit-button">
            <button type="submit" class="btn submit-button" :disabled="text.length != 0">投稿</button>
        </div>
    </form>
</template>

<script>
export default {
    data() {
        return {
            text: ''
        }
    },
    methods: {
        sendComment(e) {
            e.preventDefault();
            alert(this.text);
        }
    }
}
</script>