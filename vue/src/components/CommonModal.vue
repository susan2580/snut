<template>
  <div class="common-modal" v-if="showBool">
    <div class="modal-bg" @click="closeModal()"></div>
    <div class="modal-area">
      <img class="x-button" src="@/assets/modal/Close-Line.png" alt="x_img" @click="closeModal()">
      <div class="modal-container">

        <!-- modal-header 구간 -->
        <div class="modal-header">
          <div class="modal-hashtag">
            <common-tag 
                v-for="(tag, idx) in colCuData.hashtag" 
                width="150" 
                height="40"
                marginRight="15"
                :tagName="tag.tag" 
                :key="idx" />
          </div>
          <div class="modal-iconSet">
            <img src="@/assets/modal/Like-Line.png" alt="unlike_img" v-if="isShowing"
                 @click="like" >
            <img src="@/assets/modal/Like-Line2.png" alt="like_img"
                 @click="like" v-if="!isShowing" width="48" height="48" class="like">
            <img src="@/assets/modal/Pin-Line.png" alt="pin_img">
            <img src="@/assets/modal/Share-Line.png" alt="share_img">
          </div>
        </div>

        <!-- modal-body 구간 -->
        <div class="modal-body">

          <!-- modal 사진 구간 -->
          <div class="modal-pic" 
              v-if="!colCuData.pickedColor"
              @click="moveToPageBoolean && moveToPage(colCuData)">
            <div class="img-container" 
                style="cursor: pointer" ref="imgContainer">
              <img
                v-for="(url, idx) in sampleImg"
                :src="$store.state.imageBaseURL + url"
                alt="cu_img"
                :key="idx" />
            </div>
            <input class="previous" type="button" value="<" @click.stop="previous()" ref="previous" disabled />
            <input class="next" type="button" value=">" @click.stop="next()" ref="next" />
          </div>

          <!-- modal 사진+글 구간 -->
          <div class="modal-content-pic" v-if="!colCuData.pickedColor">
            <p><b>{{ colCuData.collectionTitle || colCuData.curationTitle }}</b></p>
            <p><b>{{ colCuData.nickname }}</b></p>
            <p>{{ colCuData.collectionText || colCuData.curationText }}</p>
          </div>

          <!-- modal only 글 구간 -->
          <div class="modal-content-nonPic" v-if="colCuData.pickedColor">
            <TipTap class="txtEditor" ref="textEditor" :isEditable="false" :curationContents="colCuData.curationText"/>
          </div>

        </div>

      </div>
    </div>
  </div>
</template>

<script>
import CommonTag from '@/components/CommonTag.vue';
import TipTap from '@/components/TextEditor.vue'

export default {
  name: 'CommonModal',
  components: {
    CommonTag, TipTap
  },
  data() {
    return {
      showBool: false,
      colCuData: {},
      moveToPageBoolean: false,
      sampleImg: [],
      imgSlideData: {
        curPos: 0,
        position: 0,
        IMAGE_WIDTH: 400
      },
      isShowing: true,
    }
  },
  methods: {
    openModal(colCuData, moveToPageBool) {
      let imgList = null;
      if (colCuData.cuCo == "Collection") {
        let tmp = colCuData.curationList.filter(cu => { if(cu.pickedColor == null) return cu });
        imgList = tmp.map(cu => cu.imageDTOList[0].imageURL);
      } else if (colCuData.cuCo == "Curation") {
        imgList = colCuData.imageDTOList.map(i => i.imageURL );
      }

      this.sampleImg = imgList;
      this.moveToPageBoolean = moveToPageBool;
      this.showBool = true;
      this.colCuData = colCuData;
    },
    closeModal() {
      this.imgSlideData.curPos = 0;
      this.imgSlideData.position = 0;
      this.showBool = false;
      this.sampleImg = [];
    },
    moveToPage(colCuData) {
      if(colCuData.cuCo == 'Collection') {
        this.$router.push({
          path: `/ucol/${colCuData.collectionNo}/${colCuData.nickname}`
        });
      }
    },
    previous() {
      if(this.imgSlideData.curPos > 0) {
        this.$refs.next.removeAttribute("disabled");
        this.imgSlideData.position += this.imgSlideData.IMAGE_WIDTH;
        this.$refs.imgContainer.style.transform = `translateX(${this.imgSlideData.position}px`;
        this.imgSlideData.curPos -= 1;
      }
      if(this.imgSlideData.curPos == 0) {
        this.$refs.previous.setAttribute('disabled', 'true');
      }
    },
    next() {
      if(this.imgSlideData.curPos < this.sampleImg.length - 1 ) {
        this.$refs.previous.removeAttribute("disabled");
        this.imgSlideData.position -= this.imgSlideData.IMAGE_WIDTH;
        this.$refs.imgContainer.style.transform = `translateX(${this.imgSlideData.position}px`;
        this.imgSlideData.curPos += 1;
      }
      if(this.imgSlideData.curPos == this.sampleImg.length - 1 ) {
        this.$refs.next.setAttribute('disabled', 'true');
      }
    },
    like(){
      if(this.isShowing){
        this.isShowing= false;
      }else{
        this.isShowing = true;
      }
    }

  }
}
</script>

<style scoped>
.common-modal {
  position: fixed;
  left: 0;
  top: 0;
  display: block;
  width: 100%;
  height: 100%;
  z-index: 4;
}
.modal-bg {
  background: rgba(0, 0, 0, 0.3);
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: -1;
}
.modal-area {
  width: 1000px;
  height: 650px;
  background: white;
  position: fixed;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  -moz-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  -o-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  z-index: 10;
}
.modal-container {
  width: 850px;
  height: 500px;
  margin: 60px 75px 90px 75px;
}


/* modal header 구간 */
.modal-header {
  width: 100%;
  height: 100px;
  /* background: gray; */
  display: flex;
}
.modal-hashtag {
  width: 590px;
  height: 100%;
  margin-top: 5px;
  margin-right: 20px;
  display: flex;
  justify-content: flex-start;
  /* background: lightyellow; */
}
.modal-iconSet {
  width: 260px;
  height: 100%;
  display: flex;
  justify-content: flex-end;
  /* background: lightcoral; */
}
.modal-iconSet img {
  width: 48px;
  height: 48px;
}


/* modal body 구간 */
.modal-body {
  width: 100%;
  height: 400px;
  display: flex;
  justify-content:space-between ;
}


/* modal-pic 구간 */
.modal-pic {
  overflow: hidden;
  position: relative;
  z-index: 2;
}
.img-container {
  width: 400px;
  height: 400px;
  display: flex;
  /* flex-direction: row-reverse; */
  position: relative;
  transition: all 0.5s;
  z-index: 1;
}
.modal-pic img {
  width: 100%;
  height: 100%;
  object-fit:fill;
}
.previous {
  width: 50px;
  height: 50px;
  position: absolute;
  top: 175px;
  left: 20px;
  z-index: 2;
}
.next {
  width: 50px;
  height: 50px;
  position: absolute;
  top: 175px;
  right: 20px;
  z-index: 2;
}


/* modal-content 구간 */
.modal-content-pic {
  width: calc(400px - (20px * 2));
  height: calc(400px - (45px * 2));
  padding: 45px 20px;
  border: 1px solid black;
  border-radius: 12px;
  overflow: scroll;
}
.modal-content-nonPic {
  /* width: 100%;
  height: calc(100%-90px);
  padding: 45px 20px;
  border: 1px solid black;
  border-radius: 12px;
  overflow: scroll; */
}
.modal-content-nonPic p {
  padding: 0 20px;
}
.txtEditor {
  width: 800px;
  height: 400px;
  padding: 20px 20px;
  border: 1px solid black;
  border-radius: 12px;
  /* background: red; */
}

/* 기타 구간 */
.x-button {
  position: absolute;
  top: 25px;
  right: 30px;
}
.modalBodyContents * {
  font-size: 20px;
}
.modal-content-pic::-webkit-scrollbar{
  display: none;
  width: 0
}
</style>