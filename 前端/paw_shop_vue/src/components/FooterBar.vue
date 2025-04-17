<template>
  <div>
    <!-- 延伸頁腳：整合導覽、服務、法規、社群、下載、付款方式等 -->
    <v-sheet class="py-12" color="#215d1e">
      <v-container>
        <v-row>
          <v-col cols="12" sm="6" md="3">
            <h4 class="mb-2 font-weight-bold">Services</h4>
            <div class="text-subtitle-2 mb-1">My Account</div>
            <div class="text-subtitle-2 mb-1">Help Center</div>
            <div class="text-subtitle-2 mb-1">Order Tracking</div>
            <div class="text-subtitle-2 mb-1">Shipping Info</div>
            <div class="text-subtitle-2 mb-1">Returns & Refunds</div>
          </v-col>

          <v-col cols="12" sm="6" md="3">
            <h4 class="mb-2 font-weight-bold">Shopping</h4>
            <div class="text-subtitle-2 mb-1">Special Offers</div>
            <div class="text-subtitle-2 mb-1">Clearance</div>
            <div class="text-subtitle-2 mb-1">Repeat Orders</div>
            <div class="text-subtitle-2 mb-1">Gift Cards</div>
          </v-col>

          <v-col cols="12" sm="6" md="3">
            <h4 class="mb-2 font-weight-bold">Legal & Info</h4>
            <div class="text-subtitle-2 mb-1">Privacy Policy</div>
            <div class="text-subtitle-2 mb-1">Terms & Conditions</div>
            <div class="text-subtitle-2 mb-1">Cookies</div>
            <div class="text-subtitle-2 mb-1">Company Info</div>
          </v-col>

          <v-col cols="12" sm="6" md="3">
            <h4 class="mb-2 font-weight-bold">Connect</h4>
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
              <span>About</span>
            </v-btn>
            <v-btn variant="text" size="small" class="footer-btn">
              <i class="fas fa-question-circle mr-1"></i>
              <span>Help</span>
            </v-btn>
            <v-btn variant="text" size="small" class="footer-btn">
              <i class="fas fa-envelope mr-1"></i>
              <span>Contact</span>
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
