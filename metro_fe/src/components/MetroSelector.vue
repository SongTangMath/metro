<template>
  <div class="selector-wrapper">
    <span>选择城市--线路</span>
    <Cascader
      v-model="value"
      :options="options"
      @change="handleChange"
    ></Cascader>
  </div>
</template>

<script>
import { Cascader } from "element-ui";

export default {
  name: "MetroSelector",
  props: {
    metroInfos: Array,
  },
  components: {
    Cascader,
  },

  data() {
    return {
      value: [],
      cityId: 1,
      lineId: 1,
    };
  },

  computed: {
    options() {
      let cityAndLines = [];

      for (let city of this.metroInfos) {
        let option = {};

        option.label = city.cityName;
        option.value = city.cityId;
        option.children = [];

        for (let line of city.lines) {
          option.children.push({
            label: line.lineName,
            value: line.lineId,
          });
        }
        cityAndLines.push(option);
      }

      return cityAndLines;
    },
  },

  methods: {
    handleChange(value) {
      console.log(value);
    },
  },
  mounted() {},
};
</script>

<style scoped>
.selector-wrapper {
  margin-bottom: 10px;
}

.selector-wrapper span {
  margin-right: 10px;
}
</style>
