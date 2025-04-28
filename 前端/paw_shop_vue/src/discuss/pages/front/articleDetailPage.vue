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
          :isLiked="isLiked"
          :likeCount="likeCount"
          @toggle-like="toggleLike"
        >
          <div class="article-content">
            {{ article.content }}
          </div>

          <!-- 主文留言 -->
          <div v-if="mainReplies.length > 0" class="mt-4">
            <DiscussReplyItem
              v-for="reply in mainReplies"
              :key="reply.commentId"
              :memberName="reply.memberName"
              :content="reply.content"
            />
          </div>
        </DiscussArticleCard>

        <v-row justify="center" v-else-if="loading">
          <v-progress-circular indeterminate color="primary" size="50" />
        </v-row>

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
            <DiscussCommentCard
              :floor="floor.floor"
              :memberName="floor.deleted ? '系統' : floor.memberName"
              :content="floor.deleted ? '（此留言已被刪除）' : floor.content"
              :deleted="floor.deleted"
            >
              <!-- 樓中樓回覆 -->
              <template v-if="floor.replies && floor.replies.length > 0">
                <div class="pl-8 mt-2">
                  <DiscussReplyItem
                    v-for="reply in floor.replies"
                    :key="reply.commentId"
                    :memberName="reply.memberName"
                    :content="reply.content"
                  />
                </div>
              </template>
            </DiscussCommentCard>
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
import { useRoute, useRouter } from "vue-router"; // 這邊多加 router
import { useAuthStore } from "@/member/stores/auth";
import axios from "axios";
import { fetchArticleDetail } from "@/discuss/api/articleApi";
import { fetchCommentsByArticle } from "@/discuss/api/commentApi";

import DiscussArticleCard from "@/discuss/components/discussArticleCard.vue";
import DiscussCommentCard from "@/discuss/components/discussCommentCard.vue";
import DiscussReplyItem from "@/discuss/components/discussReplyItem.vue";

const route = useRoute();
const router = useRouter(); // 這邊加上
const authStore = useAuthStore();
const articleId = route.params.articleId;

const article = ref(null);
const comments = ref([]);
const loading = ref(true);

const isLiked = ref(false);
const likeCount = ref(0);

// 主文下的留言（1-1、1-2）
const mainReplies = computed(() => {
  return comments.value.filter((c) => c.parentCommentId === -1);
});

// 樓層留言（2樓以上）
const floorComments = computed(() => {
  const floors = [];
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
      replies: comment.replies || [],
    });
    expectedFloor++;
  });

  return floors;
});

// 一次處理所有初始資料
onMounted(async () => {
  try {
    const articleData = await fetchArticleDetail(articleId);
    article.value = articleData;

    const commentsData = await fetchCommentsByArticle(articleId);
    comments.value = commentsData;

    if (authStore.isLoggedIn) {
      const response = await axios.post("/api/likes/check", {
        articleId: articleId,
        memberId: authStore.memberId,
      });
      isLiked.value = response.data;
    }

    const likeCountRes = await axios.get(`/api/likes/count/${articleId}`);
    likeCount.value = likeCountRes.data;
  } catch (error) {
    console.error("載入失敗", error);
    article.value = null;
  } finally {
    loading.value = false;
  }
});

// 按下按鈕切換按讚
const toggleLike = async () => {
  if (!authStore.isLoggedIn) {
    router.push("/login");
    return;
  }

  try {
    if (isLiked.value) {
      await axios.delete("/api/likes", {
        data: { articleId, memberId: authStore.memberId },
      });
      isLiked.value = false;
      likeCount.value--;
    } else {
      await axios.post("/api/likes", {
        articleId,
        memberId: authStore.memberId,
      });
      isLiked.value = true;
      likeCount.value++;
    }
  } catch (error) {
    console.error("切換按讚失敗", error);
  }
};
</script>
