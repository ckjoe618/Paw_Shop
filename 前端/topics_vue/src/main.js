// 建立 APP
import { createApp } from "vue";
import App from "./App.vue";
const app = createApp(App);

// 使用 router 插件
import routes from "./router";
app.use(routes);

// 使用 pinia 插件
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate); // 使用持久化插件，可避免 F5 後，全部遺忘
app.use(pinia);

// vuetify 插件
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "@mdi/font/css/materialdesignicons.css";
const vuetify = createVuetify({
  components,
  directives,
});
app.use(vuetify);

// 使用 icon 插件
import "@fortawesome/fontawesome-free/css/all.css";

// 掛載 APP
app.mount("#app");
