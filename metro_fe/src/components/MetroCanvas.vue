<template>
  <div class="hello">
    <canvas id="metro-canvas" width="1800" height="600"> </canvas>
  </div>
</template>

<script>
export default {
  name: "MetroCanvas",
  props: { singleLineData: Object },
  components: {},

  data() {
    return {
      turnImg: {},
    };
  },

  methods: {
    draw() {
      let canvas = document.getElementById("metro-canvas");
      let ctx = canvas.getContext("2d");

      let stations = this.singleLineData.stations;
      let stationRadius = 10;

      //计算坐标.x轴分成(站点数+1)份.
      for (let i = 0; i < stations.length; i++) {
        //绘制站点
        ctx.beginPath();

        if (stations[i].transferLines) {
          //绘制换乘站图片
          ctx.drawImage(
            this.turnImg,
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
      }
      let allConnections = [
        ...this.singleLineData.stationConnections,
        ...this.singleLineData.stationTransfers,
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

      for (let metroGraphText of this.singleLineData.metroGraphTexts) {
        ctx.save();
        ctx.translate(metroGraphText.translateX, metroGraphText.translateY);
        ctx.rotate(metroGraphText.rotation);
        ctx.textAlign = metroGraphText.textAlign;
        ctx.font = "15px Microsoft Yahei";
        ctx.fillStyle = "black";
        ctx.fillText(
          metroGraphText.text,
          metroGraphText.xpos,
          metroGraphText.ypos
        );
        ctx.restore();
      }
    },
  },

  mounted() {
    this.turnImg = new Image();
    // https://map.bjsubway.com/subwaymap/turn.png
    this.turnImg.src = require("@/assets/turnImg.png");
  },

  watch: {
    singleLineData() {
      console.log("singleLineData changed");
      //清空画布
      let canvas = document.getElementById("metro-canvas");
      let ctx = canvas.getContext("2d");
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      this.draw();
    },
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
