<template>
  <div class="app-container">
  
  <el-row :gutter="0">
    <el-col :span="12">
      <div class="box">
     <ul>
      <li>
        <h3>本月总计{{mhq}}个灭火器，已点检{{mhqdj}}个，未点检{{mhq - mhqdj}}个。</h3>
      </li>
      <li>
        <h3>本月总计{{xhs}}个消防栓，已点检{{xhsdj}}个，未点检{{xhs - xhsdj}}个。</h3>
      </li>
      <li>
        <h3>本月总计{{bjfz}}个报警阀组，已点检{{bjfzdj}}个，未点检{{bjfz - bjfzdj}}个。</h3>
      </li>
      <li>
        <h3>本月总计{{bf}}个泵房，已点检{{bfdj}}个，未点检{{bf - bfdj}}个。</h3>
      </li>    
     </ul>
    </div>
    </el-col>
    <el-col :span="12">
      <div class="box2">
        <ul>
        <h3>其中合格的灭火器{{mhqhg}}个，不合格{{mhq - mhqhg}}个。</h3>
        <h3>其中合格的消防栓{{xhshg}}个，不合格{{xhs - xhshg}}个。</h3>
        <h3>其中合格的报警阀组{{bjfzhg}}个，不合格{{bjfz - bjfzhg}}个。</h3>
        <h3>其中合格的泵房{{bfhg}}个，不合格{{bf - bfhg}}个。</h3>   
      </ul>
      </div>
      
    </el-col>
  </el-row>

  </div>
</template>

<script>
import { listFirefighting, listFirefighting2, delFirefighting, addFirefighting, updateFirefighting, qrImg, qrImgList} from "@/api/fire/firefighting";
import { getToken, setToken, removeToken } from '@/utils/auth'
import { infor ,listRecord} from "@/api/record/record";

export default {
  name: "Firefighting",
  data() {
    return {
      mhq:0,
      mhqdj:0,
      mhqhg:0,
      xhs:0,
      xhsdj:0,
      xhshg:0,
      bjfz:0,
      bjfzdj:0,
      bjfzhg:0,
      bf:0,
      bfdj:0,
      bfhg:0,
      loading: true,
      firefighting:{
        // pointCheck: null,
        // deviceGroup: null,
        checkRecords: this.getCurrentDate(),
      },
      record: {
        fireId: null,
        qualified: null,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    async getList() {
      this.loading = true;
      // 重置统计，避免重复进入页面或刷新造成累计
      this.mhq = 0;
      this.mhqdj = 0;
      this.mhqhg = 0;
      this.xhs = 0;
      this.xhsdj = 0;
      this.xhshg = 0;
      this.bjfz = 0;
      this.bjfzdj = 0;
      this.bjfzhg = 0;
      this.bf = 0;
      this.bfdj = 0;
      this.bfhg = 0;
      try {
        // 并发获取：设备列表 + 当月合格记录列表（单次请求，避免逐条请求导致超时）
        const [fireRes, recordRes] = await Promise.all([
          listFirefighting2(this.firefighting),
          // 记录列表：按当月与合格筛选，尽量拉取足量数据（如后端分页可用）
          listRecord({
            // 后端实际取值为“合格/不合格”，这里直接按“合格”过滤
            qualified: '合格',
            checkRecords: this.getCurrentDate(),
            pageNum: 1,
            pageSize: 9999
          })
        ]);

        const rows = Array.isArray(fireRes && fireRes.rows) ? fireRes.rows : [];
        const recordRows = Array.isArray(recordRes && recordRes.rows) ? recordRes.rows : [];

        // 构建当月合格记录的 fireId 集合
        const qualifiedFireIdSet = new Set();
        for (let r = 0; r < recordRows.length; r++) {
          const q = recordRows[r] && recordRows[r].qualified;
          if (recordRows[r] && q === '合格') {
            qualifiedFireIdSet.add(recordRows[r].fireId);
          }
        }

        for (let i = 0; i < rows.length; i++) {   // 当月
          // if(response.rows[i].deviceGroup == "灭火器" && response.rows[i].pointCheck == "是" ){
          //   this.mhqdj +=1; 
          //     const record = {
          //       fireId: response.rows[i].fireId,
          //       qualified: null,
          //       checkRecords: this.getCurrentDate(),
          //     }
          //     listRecord(record).then(res=>{
                     
          //     })
            
          // }else if(response.rows[i].deviceGroup == "消防栓" && response.rows[i].pointCheck == "是" ){
          //   this.xhsdj +=1; 
          // }else if(response.rows[i].deviceGroup == "阀组间" && response.rows[i].pointCheck == "是" ){
          //   this.bjfzdj +=1; 
          // }else if(response.rows[i].deviceGroup == "水泵房" && response.rows[i].pointCheck == "是" ){
          //   this.bfdj +=1; 
          // }
          if(rows[i].deviceGroup == "灭火器" ){
            this.mhq +=1; 
            if(rows[i].pointCheck == "是"){
              this.mhqdj +=1; 
              const record = {
                fireId: rows[i].fireId,
                qualified: null,
                checkRecords: this.getCurrentDate(),
              }
              // infor(record).then(res=>{
                
              // })
              // 移除逐条查询，避免大量并发请求导致超时
              // 统计合格数：当月合格记录中包含该 fireId
              if (qualifiedFireIdSet.has(rows[i].fireId)) {
                this.mhqhg += 1;
              }
            }
          }else if(rows[i].deviceGroup == "消防栓" ){
            this.xhs +=1; 
            if(rows[i].pointCheck == "是" ){
              this.xhsdj +=1; 
              if (qualifiedFireIdSet.has(rows[i].fireId)) {
                this.xhshg += 1;
              }
            }
          }else if(rows[i].deviceGroup == "阀组间" ){
            this.bjfz +=1; 
            if(rows[i].pointCheck == "是" ){
              this.bjfzdj +=1; 
              if (qualifiedFireIdSet.has(rows[i].fireId)) {
                this.bjfzhg += 1;
              }
            }
          }else if(rows[i].deviceGroup == "水泵房" ){
            this.bf +=1; 
            if(rows[i].pointCheck == "是" ){
              this.bfdj +=1; 
              if (qualifiedFireIdSet.has(rows[i].fireId)) {
                this.bfhg += 1;
              }
            }
          }
        }
        //   this.firefighting.deviceGroup = "灭火器";
        // this.firefighting.deviceGroup = "消防栓";
        // this.firefighting.deviceGroup = "阀组间";
        // this.firefighting.deviceGroup = "水泵房";
      } catch (e) {
        // 接口超时等异常已在全局拦截器提示，这里仅保证页面不崩溃
      } finally {
        this.loading = false;
      }
    },

    /** 提交按钮 */
    submitForm() {

    },

    //本月
    getCurrentDate() {
      const date = new Date();
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份是从0开始的，所以要加1，并且需要补0
      const day = String(date.getDate()).padStart(2, '0'); // 日期补0
      return `${year}-${month}`;
    }
    
  }
};
</script>

<style>
.box{
  display: flex; 
  justify-content: center; 
  white-space: nowrap;
}
.box2{
  /* text-align: center; */
  /* justify-content: center; */
  align-items: center; 
  display: flex; 
}
</style>