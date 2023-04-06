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
    cityAndLines: Array,
  },
  components: {
    Cascader,
  },

  data() {
    return {
      value: [],
      cityId: 0,
      lineId: 0,
    };
  },

  computed: {
    options() {
      let cityAndLineOptions = [];

      for (let city of this.cityAndLines) {
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
        cityAndLineOptions.push(option);
      }

      return cityAndLineOptions;
    },
  },

  methods: {
    handleChange(value) {
      this.cityId = value[0];
      this.lineId = value[1];
      this.$emit("setCityAndLineId", this.cityId, this.lineId);
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
