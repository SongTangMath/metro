<template>
  <div id="app">
    <MetroSelector
      :cityAndLines="cityAndLines"
      @setCityAndLineId="setCityAndLineId"
   
    />
    <MetroCanvas :singleLineData="singleLineData" />
  </div>
</template>

<script>
import MetroCanvas from "./components/MetroCanvas.vue";
import MetroSelector from "./components/MetroSelector.vue";
import axios from "axios";

export default {
  name: "App",
  components: {
    MetroCanvas,
    MetroSelector,
  },

  data() {
    return {
      cityAndLines: [],
      singleLineData: {
        stations: [],
        stationConnections: [],
        stationTransfers: [],
        transferTexts: [],
      },
      cityId: 1, //当前选中的城市和线路
      lineId: 1,
    };
  },

  methods: {
    setCityAndLineId(cityId, lineId) {
      this.cityId = cityId;
      this.lineId = lineId;
      console.log(`new cityId ${cityId} lineId ${lineId}`);
      this.refreshSingleLineData();
    },

    refreshSingleLineData() {
      axios
        .post("http://localhost:8080/singleLineMetroInfo", {
          cityId: this.cityId,
          lineId: this.lineId,
        })
        .then((response) => {
          this.singleLineData = response.data;
        })
        .catch(function (error) {
          // 请求失败处理
          console.log(error);
        });
    },
  },

  mounted() {
    axios
      .get("http://localhost:8080/allCityMetroInfo")
      .then((response) => {
        this.cityAndLines = response.data.metroInfos;
      })
      .catch(function (error) {
        // 请求失败处理
        console.log(error);
      });
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
