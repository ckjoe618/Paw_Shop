<template>
  <div class="dashboard-container">
    <div
      v-if="exporting"
      class="report-header mb-6 pa-4"
      style="
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-bottom: 1px solid #ddd;
      "
    >
      <div class="d-flex align-center">
        <img
          src="@/member/assets/images/PawShop_white_logo.png"
          alt="PawShop Logo"
          height="40"
          class="mr-2"
        />
        <div style="font-size: 20px; font-weight: bold">
          PawShop 會員分析報告
        </div>
      </div>

      <div style="font-size: 14px; color: #555">匯出日期：{{ today }}</div>
    </div>

    <v-row class="m-auto">
      <v-col cols="12" class="d-flex justify-end">
        <v-btn v-if="!exporting" color="primary" @click="exportToPdf">
          匯出分析報告 PDF
        </v-btn>
      </v-col>
    </v-row>
    <v-container fluid>
      <v-row>
        <!-- 📈 會員成長折線圖 -->
        <v-col cols="12" md="8">
          <v-card class="pa-4" elevation="4" height="400px">
            <LineChart
              v-if="chartReady.growth"
              :data="chartData.growth"
              :options="lineChartOptions"
            />
            <v-skeleton-loader v-else type="card" height="400px" />
          </v-card>
        </v-col>

        <!-- 🥧 性別比例圓餅圖 -->
        <v-col cols="12" md="4">
          <v-card class="pa-4" elevation="4" height="400px">
            <PieChart
              v-if="chartReady.gender"
              :data="chartData.gender"
              :options="pieChartOptions"
            />
            <v-skeleton-loader v-else type="card" height="400px" />
          </v-card>
        </v-col>
      </v-row>
      <v-row class="mt-4">
        <v-col cols="12">
          <v-card class="pa-4" elevation="4" height="400px">
            <BarChart
              v-if="chartReady.city"
              :data="chartData.city"
              :options="barChartOptions"
            />
            <v-skeleton-loader v-else type="card" height="400px" />
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script setup>
import * as api from "@/api/memberApi/AdminApi";
import { ref, onMounted, nextTick } from "vue";
import { Line, Pie, Bar } from "vue-chartjs";
import ChartDataLabels from "chartjs-plugin-datalabels";
import html2canvas from "html2canvas";
import jsPDF from "jspdf";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  ArcElement,
  CategoryScale,
  LinearScale,
  PointElement,
  BarElement,
} from "chart.js";

ChartJS.register(
  Title, // 圖表標題
  Tooltip, // 滑鼠懸停顯示提示框
  Legend, // 顯示圖例（線條的說明）
  ChartDataLabels, // 文字標籤
  LineElement, // 線條圖的核心元素
  ArcElement, // 圓餅圖的圓弧
  CategoryScale, // X 軸分類刻度（例如 1月、2月）
  LinearScale, // Y 軸數值刻度
  PointElement, // 線上的每一個圓點
  BarElement // 長條圖的長條
);
// 組件命名
const LineChart = Line;
const PieChart = Pie;
const BarChart = Bar;

const exporting = ref(false);
const today = new Date().toLocaleDateString("zh-TW", {
  year: "numeric",
  month: "2-digit",
  day: "2-digit",
});

// 資料
const chartData = ref({
  growth: null,
  gender: null,
  city: null,
});
const chartReady = ref({
  growth: false,
  gender: false,
  city: false,
});

// 折線圖設定
const lineChartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { position: "bottom" },
    title: {
      display: true,
      text: "每月會員成長趨勢",
    },
    tooltip: {
      callbacks: {
        label: (ctx) => `${ctx.dataset.label}: ${ctx.raw} 人`,
      },
    },
    datalabels: {
      anchor: "end",
      align: "top",
      font: {
        weight: "bold",
      },
      color: "#444",
    },
  },
  scales: {
    y: {
      beginAtZero: true,
    },
  },
});

// 圓餅圖設定
const pieChartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { position: "bottom", reverse: true },
    title: {
      display: true,
      text: "會員性別比例分析",
    },
    datalabels: {
      formatter: (value, ctx) => {
        const total = ctx.chart.data.datasets[0].data.reduce(
          (a, b) => a + b,
          0
        );
        const percentage = ((value / total) * 100).toFixed(1);
        return `${percentage}%`;
      },
      color: "#fff",
      font: { weight: "bold" },
    },
    tooltip: {
      callbacks: {
        label: (ctx) => `${ctx.dataset.label}: ${ctx.raw} 人`,
      },
    },
  },
});

// 長條圖設定
const barChartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { display: false },
    title: {
      display: true,
      text: "城市會員分布",
    },
    tooltip: {
      callbacks: {
        label: (ctx) => `${ctx.dataset.label}: ${ctx.raw} 人`,
      },
    },
    datalabels: {
      anchor: "end",
      align: "end",
      font: {
        weight: "bold",
      },
      color: "#555",
    },
  },
  scales: {
    y: {
      beginAtZero: true,
    },
  },
});

onMounted(async () => {
  // 折線圖: 會員成長資料 { labels, total }
  const growth = await api.apiMemberGrowthAnalysis();
  const totalGrowth = growth.total.reduce((a, b) => a + b);
  lineChartOptions.value.plugins.title.text = `每月會員成長趨勢（共 ${totalGrowth} 人）`;
  chartData.value.growth = {
    labels: growth.labels,
    datasets: [
      {
        label: "會員新增數",
        data: growth.total,
        fill: false,
        borderColor: "rgb(75, 192, 192)",
        tension: 0.3,
        pointRadius: 5, // ⭕ 圓點大小
        pointHoverRadius: 7,
        borderWidth: 2, // ➕ 線條粗細
        fill: false,
      },
    ],
  };
  chartReady.value.growth = true;

  // 圓餅圖：性別比例資料 { labels: ["男", "女"], total: [70, 30] }
  const gender = await api.apiMemberGenderAnalysis();
  const totalGender = gender.total.reduce((a, b) => a + b);
  pieChartOptions.value.plugins.title.text = `會員性別比例分析（共 ${totalGender} 人）`;
  chartData.value.gender = {
    labels: gender.labels,
    datasets: [
      {
        label: "人數",
        data: gender.total,
        backgroundColor: ["#FF6384", "#42A5F5"],
        borderWidth: 2,
        hoverOffset: 10,
      },
    ],
  };
  chartReady.value.gender = true;

  // 長條圖：城市會員分布資料 { labels, total }
  const city = await api.apiMemberCityDistribution();
  const totalCity = city.total.reduce((a, b) => a + b);
  barChartOptions.value.plugins.title.text = `城市會員分布（共 ${totalCity} 人）`;
  chartData.value.city = {
    labels: city.labels,
    datasets: [
      {
        label: "會員數",
        data: city.total,
        backgroundColor: "#90CAF9",
        borderColor: "#42A5F5",
        borderWidth: 1,
        backgroundColor: city.total.map((v) => (v > 4 ? "#42A5F5" : "#B0BEC5")),
      },
    ],
  };
  chartReady.value.city = true;
});

const exportToPdf = async () => {
  exporting.value = true; // 隱藏按鈕

  await nextTick(); // 等畫面重新渲染完成

  const el = document.querySelector(".dashboard-container");
  const canvas = await html2canvas(el, {
    useCORS: true,
    scale: 3, // 提高解析度
  });

  const imgData = canvas.toDataURL("image/png");
  const pdf = new jsPDF("p", "mm", "a4");
  const pageWidth = pdf.internal.pageSize.getWidth();
  const pageHeight = pdf.internal.pageSize.getHeight();

  const imgWidth = pageWidth;
  const imgHeight = (canvas.height * imgWidth) / canvas.width;

  let position = 0;
  const dateStr = new Date().toLocaleDateString("zh-TW").replaceAll("/", "-");
  const filename = `會員分析報告_${dateStr}.pdf`;

  // 多頁處理
  if (imgHeight <= pageHeight) {
    pdf.addImage(imgData, "PNG", 0, 0, imgWidth, imgHeight);
  } else {
    // 多頁分段
    let remainingHeight = imgHeight;

    while (remainingHeight > 0) {
      pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
      remainingHeight -= pageHeight;
      position -= pageHeight;
      if (remainingHeight > 0) pdf.addPage();
    }
  }

  pdf.save(filename);
  exporting.value = false; // 顯示按鈕
};
</script>
