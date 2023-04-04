<template>
  <div id="app">
    <MetroSelector :metroInfos="metroInfos" />
    <MetroCanvas :metroInfos="metroInfos" />
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
      metroInfos: [],
    };
  },

  created() {
    axios
      .get("http://localhost:8080/allCityMetroInfo")
      .then((response) => {
        this.metroInfos = response.data.metroInfos;
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
