<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useAuthStore } from "@/member/stores/auth";
import axios from "axios";
import Swal from "sweetalert2";

import DiscussArticleCard from "@/discuss/components/discussArticleCard.vue";
import DiscussCommentCard from "@/discuss/components/discussCommentCard.vue";
import DiscussReplyItem from "@/discuss/components/discussReplyItem.vue";
import CommentInput from "@/discuss/components/commentInput.vue";

import { fetchArticleDetail } from "@/discuss/api/articleApi";
import { fetchCommentsByArticle } from "@/discuss/api/commentApi";

const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();
const articleId = route.params.articleId;

const article = ref(null);
const comments = ref([]);
const loading = ref(true);

const isLiked = ref(false);
const likeCount = ref(0);

const commentLikeStatusMap = ref({});
const commentLikeCountMap = ref({});

const loadComments = async () => {
  try {
    const commentsData = await fetchCommentsByArticle(articleId);
    comments.value = commentsData;
  } catch (err) {
    console.error("留言載入失敗", err);
  }
};

// 主文留言（1-1、1-2）
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

const handleDelete = async () => {
  if (!authStore.isLoggedIn) {
    router.push("/login");
    return;
  }

  const result = await Swal.fire({
    title: "確定要刪除這篇文章嗎？",
    text: "刪除後將無法復原！",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#d33",
    cancelButtonColor: "#3085d6",
    confirmButtonText: "是，刪除",
    cancelButtonText: "取消",
  });

  if (result.isConfirmed) {
    try {
      await axios.delete(
        `/api/articles/${article.value.articleId}/member/${authStore.memberId}`
      );
      await Swal.fire("已刪除！", "文章已成功刪除。", "success");
      router.push("/discuss");
    } catch (error) {
      console.error("刪除文章失敗", error);
      Swal.fire("錯誤", "刪除失敗，請稍後再試", "error");
    }
  }
};

const handleDeleteComment = async (commentId) => {
  if (!authStore.isLoggedIn) {
    router.push("/login");
    return;
  }

  const result = await Swal.fire({
    title: "確定要刪除這則留言嗎？",
    text: "刪除後將無法恢復",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "是，刪除！",
    cancelButtonText: "取消",
  });

  if (result.isConfirmed) {
    try {
      await axios.delete("/api/comments", {
        data: {
          commentId,
          memberId: authStore.memberId,
        },
      });
      await loadComments();
      Swal.fire("刪除成功", "留言已被刪除", "success");
    } catch (err) {
      console.error("刪除留言失敗", err);
      Swal.fire("錯誤", "刪除留言失敗", "error");
    }
  }
};

// 主要初始化
onMounted(async () => {
  try {
    const articleData = await fetchArticleDetail(articleId);
    article.value = articleData;

    const commentsData = await fetchCommentsByArticle(articleId);

    // ✅ 防止錯誤 memberPhoto 問題，先清空
    commentsData.forEach((comment) => {
      comment.memberPhoto = null;
    });

    comments.value = commentsData;

    // 主文按讚資料
    if (authStore.isLoggedIn) {
      const response = await axios.post("/api/likes/check", {
        articleId: articleId,
        memberId: authStore.memberId,
      });
      isLiked.value = response.data;
    }

    const likeCountRes = await axios.get(`/api/likes/count/${articleId}`);
    likeCount.value = likeCountRes.data;

    // 留言按讚資料
    for (const comment of commentsData) {
      const commentId = comment.commentId;

      if (authStore.isLoggedIn) {
        try {
          const res = await axios.post("/api/comment-likes/check", {
            commentId: commentId,
            memberId: authStore.memberId,
          });
          commentLikeStatusMap.value[commentId] = res.data;
        } catch (error) {
          console.error(`查詢留言${commentId}是否按讚失敗`, error);
        }
      }

      try {
        const res = await axios.get(`/api/comment-likes/count/${commentId}`);
        commentLikeCountMap.value[commentId] = res.data;
      } catch (error) {
        console.error(`查詢留言${commentId}讚數失敗`, error);
      }
    }
  } catch (error) {
    console.error("載入失敗", error);
    article.value = null;
  } finally {
    loading.value = false;
  }
});

// 主文按讚切換
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

// 留言按讚切換
const toggleCommentLike = async (commentId) => {
  if (!authStore.isLoggedIn) {
    router.push("/login");
    return;
  }

  try {
    if (commentLikeStatusMap.value[commentId]) {
      await axios.delete("/api/comment-likes", {
        data: { commentId: commentId, memberId: authStore.memberId },
      });
      commentLikeStatusMap.value[commentId] = false;
      commentLikeCountMap.value[commentId]--;
    } else {
      await axios.post("/api/comment-likes", {
        commentId: commentId,
        memberId: authStore.memberId,
      });
      commentLikeStatusMap.value[commentId] = true;
      commentLikeCountMap.value[commentId]++;
    }
  } catch (error) {
    console.error("切換留言按讚失敗", error);
  }
};
</script>

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
          :memberId="article.memberId"
          :articleId="article.articleId"
          :isLiked="isLiked"
          :likeCount="likeCount"
          @toggle-like="toggleLike"
          @delete="handleDelete"
        >
          <!-- slot（預設插槽：內文） -->
          <div class="article-content mb-2">
            {{ article.content }}
          </div>

          <!-- slot name="footer"（留言與留言框） -->
          <template #footer>
            <div v-if="mainReplies.length > 0" class="mt-4">
              <DiscussReplyItem
                v-for="reply in mainReplies"
                :key="reply.commentId"
                :memberName="reply.memberName"
                :content="reply.content"
              />
            </div>
            <CommentInput
              :articleId="+articleId"
              :parentCommentId="-1"
              @success="loadComments"
            />
          </template>
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

        <CommentInput
          :articleId="+articleId"
          :parentCommentId="null"
          :onSuccess="loadComments"
        />

        <div v-if="floorComments.length > 0">
          <div
            v-for="floor in floorComments"
            :key="floor.commentId"
            class="mb-6"
          >
            <DiscussCommentCard
              :floor="floor.floor"
              :memberId="floor.memberId"
              :memberName="floor.deleted ? '系統' : floor.memberName"
              :content="floor.deleted ? '（此留言已被刪除）' : floor.content"
              :deleted="floor.deleted"
              :isLiked="commentLikeStatusMap[floor.commentId] || false"
              :likeCount="commentLikeCountMap[floor.commentId] || 0"
              @toggle-like="() => toggleCommentLike(floor.commentId)"
              @delete="() => handleDeleteComment(floor.commentId)"
            >
              <!-- 樓中樓留言 -->
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

              <!-- 🧩 對樓層回覆（4-1、5-1） -->
              <CommentInput
                v-if="typeof floor.commentId === 'number'"
                :articleId="+articleId"
                :parentCommentId="floor.commentId"
                @success="loadComments"
              />
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
