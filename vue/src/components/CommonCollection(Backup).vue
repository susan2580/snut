<template>
  <div class="collection" @mouseover="inCuration()" @mouseleave="outCuration()">

    <div class="img-area">
      <img :class="cuSelect" :src="info.src[0]" alt="sample_img">
      <img :class="cuSelect" :src="info.src[1]" alt="sample_img" v-if="info.src[1]">
      <div class="replace" v-if="!info.src[1]"></div>
      <folder-icon width="180" height="180" v-if="false"/>
    </div>

    <div class="text1" v-if="hoverBool && (!storeBool || !delColBoolean || !loginBool)">
      <p>{{ '#'+info.hashTag[0] }}</p>
      <p>{{ '#'+info.hashTag[1] }}</p>
      <p>{{ '#'+info.hashTag[2] }}</p>
      <p>{{ info.modDate }}</p>
      <p>{{ info.cuCo }}</p>
    </div>
    <div class="text1" v-if="storeBool && delColBoolean && loginBool"> 
      <button @click.stop="deleteCol()">삭제</button>
      <button @click.stop="shareCol()">공유</button>
    </div>
  </div>
</template>

<script>
import FolderIcon from '@/assets/icon/FolderIcon.vue';

export default {
  name: "CommonCollection",
  components: { FolderIcon },
  props: ['info', 'id', 'delColBoolean', 'loginBool'],
  data() {
    return {
      cuSelect: 'cu-img' + this.id,
      
      // hashTag들만 뜨게 만들기
      hoverBool: false,

      // 내꺼가 아닌 다른 사람의 컬렉션을 보기
      storeBool: false,
    }
  },
  methods: {
    inCuration() {
      this.hoverBool = true;
      this.storeBool = true;
      document.querySelector('.'+this.cuSelect).classList.add('lowerBrightness');
    },
    outCuration() {
      this.hoverBool = false;
      this.storeBool = false;
      document.querySelector('.'+this.cuSelect).classList.remove('lowerBrightness');
    },
    deleteCol() {
      console.log('CommonCollection', this.id);
      this.$emit('deleteCol', this.id);
    },
    shareCol() {
      console.log("공유 버튼을 눌렀다!");
    }
  }
}
</script>

<style scoped>
.collection {
  position: relative;
}
.img-area {
  position: relative;
}
.collection img:nth-child(1) {
  position: relative;
  width: 180px;
  height: 180px;
  object-fit: cover;
  /* margin-right: 0px; */
  z-index: 1;
}
.collection img:nth-child(2) {
  width: 180px;
  height: 180px;
  position: absolute;
  left: 5px;
  top: 5px;
  object-fit: cover;
  filter: brightness(70%);
  /* -webkit-filter: blur(1px); */
  /* margin-right: 0px; */
  z-index: 0;
}
.replace {
  width: 180px;
  height: 180px;
  position: absolute;
  background: grey;
  left: 5px;
  top: 5px;
  object-fit: cover;
  filter: brightness(80%);
  -webkit-filter: blur(2px);
  /* margin-right: 0px; */
  z-index: 0;
}
.text1 {
  width: 180px;
  height: 180px;
  position: absolute;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  top: 0;
  left: 0;
  opacity: 100%;
  z-index: 3;
}
.text1 p {
  color: white;
  font-weight: bold;
  position: relative;
}
.text1 button {
  width: 70px;
  height: 35px;
  margin: 10px;
  background: #DC8552;
  color: white;
  border: none;
  border-radius: 100px;
  cursor: pointer;
}
.lowerBrightness {
  transition: all .3s;
  filter: brightness(60%);
}
</style>