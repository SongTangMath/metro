<template>
  <div class="hello">
    <canvas id="metro-canvas" width="2000" height="500"> </canvas>
  </div>
</template>

<script>
import guangzhou_3 from "/static/guangzhou_3.json";

export default {
  name: "MetroCanvas",
  props: {},
  components: {},

  data() {
    return {
      metroData: guangzhou_3,
    };
  },

  methods: {
    draw(turnImg) {
      let canvas = document.getElementById("metro-canvas");
      let ctx = canvas.getContext("2d");

      let stations = this.metroData.stations;
      let stationRadius = 10;

      //计算坐标.x轴分成(站点数+1)份.
      for (let i = 0; i < stations.length; i++) {
        //绘制站点
        ctx.beginPath();

        if (stations[i].transferLines) {
          //绘制换乘站图片
          ctx.drawImage(
            turnImg,
            stations[i].xpos - stationRadius,
            stations[i].ypos - stationRadius,
            2 * stationRadius,
            2 * stationRadius
          );
        } else {
          ctx.arc(
            stations[i].xpos,
            stations[i].ypos,
            stationRadius,
            0,
            2 * Math.PI,
            false
          );
        }

        ctx.strokeStyle = "black";
        ctx.stroke();
        ctx.closePath();

        //绘制站名 https://stackoverflow.com/questions/3167928/drawing-rotated-text-on-a-html5-canvas

        ctx.save();
        ctx.translate(stations[i].xpos, stations[i].ypos);
        ctx.rotate(-Math.PI / 4);
        ctx.textAlign = "left";
        ctx.font = "15px Microsoft Yahei";
        ctx.fillStyle = "black";
        ctx.fillText(stations[i].stationName, 25, 0);
        ctx.restore();
      }
      let allConnections = [
        ...this.metroData.stationConnections,
        ...this.metroData.stationTransfers,
      ];
      for (let connectionLine of allConnections) {
        ctx.beginPath();
        ctx.fillStyle = connectionLine.color;
        for (let line of connectionLine.lines) {
          if (line.lineType === "line") {
            ctx.lineTo(line.lineEndX, line.lineEndY);
          } else {
            ctx.arc(
              line.arcCenterX,
              line.arcCenterY,
              line.radius,
              line.arcStartAngle,
              line.arcEndAngle,
              line.isCounterClockWise
            );
          }
        }
        ctx.stroke();
        ctx.fill();
        ctx.closePath();
      }

      for (let transferText of this.metroData.transferTexts) {
        ctx.save();
        ctx.translate(transferText.xpos, transferText.ypos);
        // ctx.rotate(-Math.PI / 4);
        ctx.textAlign = "center";
        ctx.font = "15px Microsoft Yahei";
        ctx.fillStyle = "black";
        ctx.fillText(transferText.text, 15, 0);
        ctx.restore();
      }
    },
  },

  mounted() {
    let turnImg = new Image();
    let currentComponent = this;
    // https://map.bjsubway.com/subwaymap/turn.png
    turnImg.src = require("@/assets/turnImg.png");

    turnImg.onload = function () {
      currentComponent.draw(turnImg);
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

canvas {
  border: 1px solid #000;
  margin: 0 auto;
  display: block;
}
</style>
