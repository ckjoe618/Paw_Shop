<template>
  <div>
    <!-- 延伸頁腳：整合導覽、服務、法規、社群、下載、付款方式等 -->
    <v-sheet class="py-12" color="#215d1e">
      <v-container>
        <v-row>
          <v-col cols="12" sm="6" md="3">
            <h4 class="mb-2 font-weight-bold">服務</h4>
            <div class="text-subtitle-2 mb-1">帳號中心</div>
            <div class="text-subtitle-2 mb-1">幫助中心</div>
            <div class="text-subtitle-2 mb-1">物流追蹤</div>
            <div class="text-subtitle-2 mb-1">購物須知</div>
            <div class="text-subtitle-2 mb-1">常見問答</div>
          </v-col>

          <v-col cols="12" sm="6" md="3">
            <h4 class="mb-2 font-weight-bold">購物</h4>
            <div class="text-subtitle-2 mb-1">特別企劃</div>
            <div class="text-subtitle-2 mb-1">新品報報</div>
            <div class="text-subtitle-2 mb-1">禮物卡</div>
          </v-col>

          <v-col cols="12" sm="6" md="3">
            <h4 class="mb-2 font-weight-bold">法規資訊</h4>
            <div class="text-subtitle-2 mb-1">隱私權政策</div>
            <div class="text-subtitle-2 mb-1">各項條款</div>
            <div class="text-subtitle-2 mb-1">Cookies</div>
            <div class="text-subtitle-2 mb-1">公司介紹</div>
          </v-col>

          <v-col cols="12" sm="6" md="3">
            <h4 class="mb-2 font-weight-bold">聯絡我們</h4>
            <div class="d-flex align-center mb-2">
              <v-btn icon><i class="fab fa-facebook-f"></i></v-btn>
              <v-btn icon><i class="fab fa-instagram"></i></v-btn>
              <v-btn icon><i class="fab fa-youtube"></i></v-btn>
              <v-btn icon><i class="fab fa-line"></i></v-btn>
            </div>
          </v-col>
        </v-row>
      </v-container>
    </v-sheet>

    <!-- 傳統頁腳底部 -->
    <v-footer color="green darken-4" dark class="pa-6">
      <v-container>
        <v-row>
          <v-col cols="12" sm="6">
            <i class="fas fa-paw mr-2"></i>
            &copy; {{ new Date().getFullYear() }} PawShop
          </v-col>
          <v-col cols="12" sm="6" class="d-flex justify-end">
            <v-btn variant="text" size="small" class="footer-btn">
              <i class="fas fa-info-circle mr-1"></i>
              <span>關於PawShop</span>
            </v-btn>
            <v-btn variant="text" size="small" class="footer-btn">
              <i class="fas fa-question-circle mr-1"></i>
              <span>幫助中心</span>
            </v-btn>
            <v-btn variant="text" size="small" class="footer-btn">
              <i class="fas fa-envelope mr-1"></i>
              <span>聯繫客服</span>
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-footer>
  </div>
</template>

<script setup></script>

<style scoped>
i {
  font-size: 14px;
  vertical-align: middle;
}
.footer-btn {
  color: white !important;
  text-transform: none;
  font-size: 14px;
  margin-left: 8px;
}
.footer-btn span {
  color: white;
}
</style>
