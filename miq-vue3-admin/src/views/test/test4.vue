<template>
  <div class="container">
    <div class="wrapper">
      <div class="text-input">
        <textarea spellcheck="false" placeholder="输入文本(enter)" v-model="state.zhStr"></textarea>
        <textarea spellcheck="false" readonly disabled class="to-text" placeholder="翻译结果(result)">{{ state.enStr }}</textarea>
      </div>
      <div class="controls">
        <el-space :size="18" spacer=" ">
          <el-icon><VideoPlay /></el-icon>
          <el-icon><CopyDocument /></el-icon>
          <el-input style="width: 150px" disabled placeholder="auto" />
          <el-icon><ArrowRightBold /></el-icon>
          <el-select v-model="state.language" class="!w-[150px]">
            <el-option value="zh" label="中文"></el-option>
            <el-option value="en" label="英文"></el-option>
            <el-option value="cht" label="繁体"></el-option>
            <el-option value="kor" label="韩语"></el-option>
          </el-select>
          <el-icon><VideoPlay /></el-icon>
          <el-icon><CopyDocument /></el-icon>
        </el-space>
      </div>
    </div>
    <button @click="toEn">翻译</button>
  </div>
</template>

<script setup lang="ts">
import { md5 } from 'js-md5'; 
import { jsonp } from 'vue-jsonp'

const state = reactive({
    zhStr: '',
    enStr: '',
    language: 'en'
});

const toEn = () => {
    const url = 'https://api.fanyi.baidu.com/api/trans/vip/translate';
    const appid = '20240511002048054';//appid
    const salt = suijishu();
    const secretKey = 'wNSELbj9yLR5acBt_fqi';//秘钥
    let str = appid + state.zhStr + salt + secretKey
    let sign = md5(str)
    jsonp(url, {
        q: state.zhStr,
        from: 'auto',
        to: state.language,
        appid: appid,
        salt: salt,
        sign: sign,
    }).then(res => {
        state.enStr = res.trans_result[0].dst
    }).catch(err => {
        console.log('err', err);
    })
}

const suijishu = () => {
    var Randnum = '';
    for (var i = 0; i < 10; i++) {
        Randnum += Math.floor(Math.random() * 10);
    };
    return Randnum
}


</script>

<style scoped>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Poppins', sans-serif;
}
body{
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 10px;
  min-height: 100vh;
  background: #5372F0;
}
.container{
  max-width: 690px;
  width: 100%;
  padding: 30px;
  background: #fff;
  border-radius: 7px;
  box-shadow: 0 10px 20px rgba(0,0,0,0.01);
}
.wrapper{
  border-radius: 5px;
  border: 1px solid #ccc;
}
.wrapper .text-input{
  display: flex;
  border-bottom: 1px solid #ccc;
}
.text-input .to-text{
  border-radius: 0px;
  border-left: 1px solid #ccc;
}
.text-input textarea{
  height: 250px;
  width: 100%;
  border: none;
  outline: none;
  resize: none;
  background: none;
  font-size: 18px;
  padding: 10px 15px;
  border-radius: 5px;
}
.text-input textarea::placeholder{
  color: #b7b6b6;
}
.controls{
  display: flex;
  align-items: center;
  justify-content: space-between;
  list-style: none;
  padding: 12px 15px;
}

.controls .row .icons{
  width: 38%;
}
.controls .row .icons i{
  width: 50px;
  color: #adadad;
  font-size: 14px;
  cursor: pointer;
  transition: transform 0.2s ease;
  justify-content: center;
}
.controls .row.from .icons{
  padding-right: 15px;
  border-right: 1px solid #ccc;
}
.controls .row.to .icons{
  padding-left: 15px;
  border-left: 1px solid #ccc;
}
.controls .row select{
  color: #333;
  border: none;
  outline: none;
  font-size: 18px;
  background: none;
  padding-left: 5px;
}
.text-input textarea::-webkit-scrollbar{
  width: 4px;
}
.controls .row select::-webkit-scrollbar{
  width: 8px;
}
.text-input textarea::-webkit-scrollbar-track,
.controls .row select::-webkit-scrollbar-track{
  background: #fff;
}
.text-input textarea::-webkit-scrollbar-thumb{
  background: #ddd;
  border-radius: 8px;
}
.controls .row select::-webkit-scrollbar-thumb{
  background: #999;
  border-radius: 8px;
  border-right: 2px solid #ffffff;
}
.controls .exchange{
  color: #adadad;
  cursor: pointer;
  font-size: 16px;
  transition: transform 0.2s ease;
}
.controls i:active{
  transform: scale(0.9);
}
.container button{
  width: 100%;
  padding: 14px;
  outline: none;
  border: none;
  color: #fff;
  cursor: pointer;
  margin-top: 20px;
  font-size: 17px;
  border-radius: 5px;
  background: #5372F0;
}

@media (max-width: 660px){
  .container{
    padding: 20px;
  }
  .wrapper .text-input{
    flex-direction: column;
  }
  .text-input .to-text{
    border-left: 0px;
    border-top: 1px solid #ccc;
  }
  .text-input textarea{
    height: 200px;
  }
  .controls .row .icons{
    display: none;
  }
  .container button{
    padding: 13px;
    font-size: 16px;
  }
  .controls .row select{
    font-size: 16px;
  }
  .controls .exchange{
    font-size: 14px;
  }
}
</style>
