<template>
  <v-container fluid>
    <v-card>
      <v-card-title>
        所有文章
        <v-spacer></v-spacer>
        <addbtn class="ml-2" />
      </v-card-title>

      <v-card-text>
        <v-data-table
          :headers="headers"
          :items="articles"
          :items-per-page="10"
          class="elevation-1"
        >
          <!-- 會員照片 -->
          <template v-slot:item.memberPhoto="{ item }">
            <img
              v-if="!item.hidePhoto"
              :src="`/uploads/${item.memberPhoto}`"
              alt="member"
              width="50"
              @error="item.hidePhoto = true"
            />
          </template>

          <!-- 建立時間格式轉換 -->
          <template v-slot:item.createdDate="{ item }">
            {{ formatDate(item.createdDate) }}
          </template>

          <!-- 是否置頂 -->
          <template v-slot:item.pinned="{ item }">
            {{ item.pinned ? "是" : "否" }}
          </template>

          <!-- 是否精華 -->
          <template v-slot:item.featured="{ item }">
            {{ item.featured ? "是" : "否" }}
          </template>

          <!-- 操作按鈕區 -->
          <template v-slot:item.actions="{ item }">
            <div style="display: flex; gap: 4px; justify-content: center">
              <formCheckbtn />
              <formEditbtn />
              <formDeletebtn />
            </div>
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import axios from "axios";
import addbtn from "../../components/buttons/addbtn.vue";
import formCheckbtn from "../../components/buttons/formCheckbtn.vue";
import formEditbtn from "../../components/buttons/formEditbtn.vue";
import formDeletebtn from "../../components/buttons/formDeletebtn.vue";

export default {
  name: "ArticleListPage",
  components: {
    addbtn,
    formCheckbtn,
    formEditbtn,
    formDeletebtn,
  },
  data() {
    return {
      articles: [],
      headers: [
        { title: "文章編號", value: "articleId" },
        { title: "會員編號", value: "memberId" },
        { title: "會員照片", value: "memberPhoto" },
        { title: "標題", value: "title" },
        { title: "分類編號", value: "categoryId" },
        { title: "瀏覽次數", value: "viewCount" },
        { title: "置頂", value: "pinned" },
        { title: "精華", value: "featured" },
        { title: "留言數", value: "commentCount" },
        { title: "建立時間", value: "createdDate" },
        { title: "操作", value: "actions", sortable: false },
      ],
    };
  },
  methods: {
    getArticles() {
      axios
        .get("http://localhost:8080/discuss/article")
        .then((response) => {
          // 若沒有 memberPhoto 也要加上 hidePhoto 欄位以便錯誤處理
          this.articles = response.data.map((a) => ({
            ...a,
            hidePhoto: false,
          }));
        })
        .catch((error) => {
          console.error("取得文章失敗", error);
        });
    },
    formatDate(datetime) {
      return new Date(datetime).toLocaleString();
    },
  },
  mounted() {
    this.getArticles();
  },
};
</script>
