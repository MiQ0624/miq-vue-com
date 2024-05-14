<template>
  <div class="app-container">
    <div class="container">
      <div class="wrapper">
        <div class="text-input">
          <textarea spellcheck="false" placeholder="输入文本(enter)" v-model="queryParams.keyword"></textarea>
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
              <el-option value="jp" label="日语"></el-option>
              <el-option value="yue" label="粤语"></el-option>
              <el-option value="wyw" label="文言文"></el-option>
              <el-option value="fra" label="法语"></el-option>
              <el-option value="ara" label="阿拉伯语"></el-option>
              <el-option value="de" label="德语"></el-option>
              <el-option value="ru" label="西班牙语"></el-option>
              <el-option value="spa" label="俄语"></el-option>
              <el-option value="it" label="意大利语"></el-option>
            </el-select>
            <el-icon><VideoPlay /></el-icon>
            <el-icon><CopyDocument /></el-icon>
          </el-space>
        </div>
      </div>
      <button @click="toEn">在线翻译</button>
      <button @click="handleQuery()">本地搜索</button>
    </div>
    <div class="database-result">
      <!-- <el-tag type="primary" v-if="total > 20">本地数据库共查询到 {{total}} 条数据,仅显示前20条结果</el-tag> -->
      <!-- 后端返回接口数据  测试用 -->
      <!-- <el-table v-loading="loading" :data="tableData">
        <el-table-column label="类别" prop="type" align="left" width="80" />
        <el-table-column label="单词" prop="englishWord" align="left" width="120" />
        <el-table-column label="发音" prop="pronunciation" align="left" width="200"  />
        <el-table-column label="解释" prop="chineseWord" align="left" width="500"  />
        <el-table-column label="例句" prop="englishInstance1" align="left" width="1000"   />
        
        <el-table-column label="操作" fixed="right" width="150">
          <template #default="scope">
            <el-button v-hasPerm="['sys:user:edit']" type="primary" link size="small" @click="openDialog(scope.row.id)"><i-ep-edit />详情</el-button>
            <el-button v-hasPerm="['sys:user:delete']" type="primary" link size="small" @click="handleDelete(scope.row.id)"><i-ep-delete />删除</el-button>
          </template>
        </el-table-column>
      </el-table> -->

      <!-- 未查询或查询无结果时显示方式 -->
      <el-empty description="description" v-if="total < 1 || !queryParams.keyword" />

      <!-- 查询返回单个结果时显示方式 -->
      <el-card v-if="total === 1" v-for="(item, index) in tableData">
        <template #header>
          <div class="card-header">
            <el-space :size="18" spacer=" ">
              <span>{{item.englishWord}}</span>
              <span>{{item.pronunciation}}</span>
              <el-icon><VideoPlay /></el-icon>
              <span>{{item.type}}</span>
            </el-space>
            <p>{{item.chineseWord}}</p>
          </div>
        </template>
        <p>{{item.englishInstance1}}</p>
        <p>{{item.chineseInstance1}}</p>
        <p>{{item.englishInstance2}}</p>
        <p>{{item.chineseInstance2}}</p>
        <!-- <template #footer>Footer content</template> -->
      </el-card>

      <!-- 查询返回多个结果时展示方式 -->
      <div class="demo-collapse" v-if="total > 1 && queryParams.keyword">
        <!-- <el-collapse v-model="activeNames" @change="handleChange"> -->
        <el-collapse v-model="activeNames" v-for="(item, index) in tableData" @click="handleChange" accordion>
          <el-collapse-item  :title="item.englishWord" :key="item.englishWord">
            <el-space :size="18" spacer=" ">
              <span>{{item.pronunciation}}</span>
              <el-icon><VideoPlay /></el-icon>
              <span>{{item.type}}</span>
              <span>{{item.chineseWord}}</span>
            </el-space>
            <p>{{item.englishInstance1}}</p>
            <p>{{item.chineseInstance1}}</p>
            <p>{{item.englishInstance2}}</p>
            <p>{{item.chineseInstance2}}</p>
          </el-collapse-item>
          
        </el-collapse>
        <pagination v-if="total > 0" v-model:total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="handleQuery" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { md5 } from 'js-md5'; 
import { jsonp } from 'vue-jsonp'
import { getPageInfo } from '@/api/words';
import { EntityColumns, QueryFilter } from '@/api/words/types';

const state = reactive({
    enStr: '',
    language: 'en'
});

const activeNames = ref(['1'])
const handleChange = (val: string[]) => {
  console.log(val)
}

const toEn = () => {
    const url = 'https://api.fanyi.baidu.com/api/trans/vip/translate';
    const appid = '20240511002048054';//appid
    const salt = suijishu();
    const secretKey = 'wNSELbj9yLR5acBt_fqi';//秘钥
    let str = appid + queryParams.keyword + salt + secretKey
    let sign = md5(str)
    jsonp(url, {
        q: queryParams.keyword,
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
    handleQuery()
}

const suijishu = () => {
    var Randnum = '';
    for (var i = 0; i < 10; i++) {
        Randnum += Math.floor(Math.random() * 10);
    };
    return Randnum
}

const queryParams = reactive<QueryFilter>({
  pageNum: 1,
  pageSize: 10,
});

const loading = ref(false); //  加载状态
// const tableData = ref<EntityColumns[]>(); // 用户分页数据
const tableData = ref<EntityColumns[]>(); // 用户分页数据
const total = ref(0); // 数据总数

onMounted(() => {
  handleQuery();
});

function handleQuery() {
  loading.value = true;
  getPageInfo(queryParams)
    .then(({ data }) => {
      tableData.value = data.records as EntityColumns[];
      console.log(data);
      total.value = data.total;
    })
    .finally(() => {
      loading.value = false;
    });
}

function openDialog(params:any) {
  
}

function handleDelete(params:any) {
  
}

</script>

<style scoped>
.database-result{
  margin-top: 25px;
  margin-left: 30px;
  margin-right: 30px;
}

.container{
  max-width: 690px;
  margin: 0 auto;
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
