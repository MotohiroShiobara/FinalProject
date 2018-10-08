import Vue from 'vue';
import CommentForm from './components/CommentForm';

new Vue({
    el: '#vue-comment-form', // アプリをマウントする要素
    components: { CommentForm }, // Appというコンポーネントを使うよ、という宣言
    template: '<comment-form />', // el(今回は#app)の中に表示する内容
});