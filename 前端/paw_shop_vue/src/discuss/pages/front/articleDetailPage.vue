<template>
  <v-container class="py-8">
    <v-row justify="center">
      <v-col cols="12" md="8">
        <!-- 單篇文章 -->
        <DiscussArticleCard
          v-if="article"
          :title="article.title"
          :categoryName="article.categoryName"
          :memberName="article.memberName"
        >
          <div class="article-content">{{ article.content }}</div>
        </DiscussArticleCard>

        <!-- Loading -->
        <v-row justify="center" v-else-if="loading">
          <v-progress-circular indeterminate color="primary" size="50" />
        </v-row>

        <!-- 錯誤 -->
        <v-alert v-else type="error" class="mt-6">
          抱歉，找不到這篇文章。
        </v-alert>

        <v-divider class="my-6"></v-divider>

        <!-- 留言區 -->
        <h2 class="text-h6 font-weight-bold mb-4">留言區</h2>

        <div v-if="floorComments.length > 0">
          <div
            v-for="floor in floorComments"
            :key="floor.commentId"
            class="mb-6"
          >
            <!-- 樓層留言 -->
            <DiscussCommentCard
              v-if="!floor.isMainReply"
              :floor="floor.floor"
              :memberName="floor.deleted ? '系統' : floor.memberName"
              :content="floor.deleted ? '（此留言已被刪除）' : floor.content"
            />

            <!-- 主文留言（parentCommentId = 0） -->
            <DiscussReplyItem
              v-else
              :memberName="floor.memberName"
              :content="floor.content"
            />

            <!-- 樓中樓回覆 -->
            <div
              v-if="floor.replies && floor.replies.length > 0"
              class="pl-8 mt-2"
            >
              <DiscussReplyItem
                v-for="reply in floor.replies"
                :key="reply.commentId"
                :memberName="reply.memberName"
                :content="reply.content"
              />
            </div>
          </div>
        </div>

        <div v-else class="text-center grey--text mt-4">
          目前沒有留言，趕快來當第一個留言的人吧！
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import { fetchArticleDetail } from "@/discuss/api/articleApi";
import { fetchCommentsByArticle } from "@/discuss/api/commentApi";

// 引入元件
import DiscussArticleCard from "@/discuss/components/discussArticleCard.vue";
import DiscussCommentCard from "@/discuss/components/discussCommentCard.vue";
import DiscussReplyItem from "@/discuss/components/discussReplyItem.vue";

const route = useRoute();
const articleId = route.params.articleId;

const article = ref(null);
const comments = ref([]);
const loading = ref(true);

// 整理後的樓層資料
const floorComments = computed(() => {
  const floors = [];

  // 先處理主文留言 (parentCommentId === 0)
  const mainReplies = comments.value.filter((c) => c.parentCommentId === 0);
  mainReplies.forEach((reply) => {
    floors.push({
      ...reply,
      isMainReply: true,
    });
  });

  // 再處理樓層留言 (floor !== null)
  const realFloors = comments.value
    .filter((c) => c.floor !== null)
    .sort((a, b) => a.floor - b.floor);

  let expectedFloor = 1;
  realFloors.forEach((comment) => {
    while (expectedFloor < comment.floor) {
      floors.push({
        commentId: `missing-${expectedFloor}`,
        floor: expectedFloor,
        memberName: "系統",
        content: "（此留言已被刪除）",
        deleted: true,
        replies: [],
      });
      expectedFloor++;
    }

    floors.push({
      ...comment,
      replies: comments.value.filter(
        (r) => r.parentCommentId === comment.commentId
      ),
    });
    expectedFloor++;
  });

  return floors;
});

// 載入文章與留言
onMounted(async () => {
  try {
    const articleData = await fetchArticleDetail(articleId);
    article.value = articleData;

    const commentsData = await fetchCommentsByArticle(articleId);
    comments.value = commentsData;
  } catch (error) {
    console.error("載入失敗", error);
    article.value = null;
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.article-content {
  font-size: 16px;
  line-height: 1.8;
  white-space: pre-wrap; /* 保留換行 */
}
</style>
