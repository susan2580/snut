<template>
  <div class="commu-block" @click="moveToPage()">
      <p class="commu-num">
        {{list.id}}
      </p>
      <div class="commu-thum">
        <img :src="list.img" alt="thumsnail">
      </div>
      <div class="commu-text-area">
        <p class="commu-title">{{list.title}}</p>
        <p class="commu-text">{{list.text}}</p>
    </div>
    <p class="commu-comment">
      댓글갯수({{list.comment}})
    </p>
  </div>
</template>

<script>
export default {
  name: "CommunityList",
  props:[
      "list"
  ],
  methods: {
    moveToPage() {
      this.$router.push('/com/in');
    }
  }
}
</script>

<style scoped>
.commu-block{
  display: flex;
  align-items: center;
  width: 800px;
  height: 135px;
  background: white;
  border-bottom: 2px solid #D9D9D9;
  cursor: pointer;
}
.commu-block:hover{
  background: lightgrey;
}
.commu-num{
  width: 50px;
  font-weight: bold;
  font-size: 30px;
  color: #868686;
  margin-left: 40px;
  margin-right: 20px;
}
.commu-thum{
  display: flex;
  width: 80px;
  height: 80px;
  background: white;
  border: 1px solid #000000;
  margin-right: 20px;
  align-items: center;
  justify-content: center;

}
.commu-text-area{
  width: 400px;
}
.commu-title{
  overflow: hidden;
  text-overflow: ellipsis;
  font-weight: 700;
  font-size: 30px;
  margin-right: 20px;
}
.commu-text{
  overflow: hidden;
  text-overflow: ellipsis;
  font-weight: 500;
  font-size: 18px;
  margin-right: 20px;
}
.commu-comment{
  width: 200px;
  display: flex;
  align-items: flex-end;
  justify-content: flex-end;
  padding-right: 20px;

}
</style>