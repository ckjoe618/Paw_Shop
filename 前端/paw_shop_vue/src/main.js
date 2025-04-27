// 建立 APP
import "@/plugins/axios";
import { createApp } from "vue";
import App from "./App.vue";
const app = createApp(App);

// 使用 router 插件
import router from "./router";
app.use(router);

// 使用 pinia 插件
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate); // 使用持久化插件，能避免 F5 後，全部遺忘
app.use(pinia);

// 使用 vuetify 插件
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "vuetify/styles";
import "@mdi/font/css/materialdesignicons.css";
const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: "mdi",
  },
});
app.use(vuetify);

// 使用 Font Awesome css
import "@fortawesome/fontawesome-free/css/all.css";

// 使用 Bootstrap
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";

// 掛載 APP
app.mount("#app");
