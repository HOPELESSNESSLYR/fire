<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备型号" prop="deviceModel">
        <el-input
          v-model="queryParams.deviceModel"
          placeholder="请输入设备型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="放置位置" prop="locate">
        <el-input
          v-model="queryParams.locate"
          placeholder="请输入放置位置"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态是否有效" prop="status">
        <el-input
          v-model="queryParams.status"
          placeholder="请输入状态"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备分组" prop="deviceGroup">
        <el-input
          v-model="queryParams.deviceGroup"
          placeholder="请输入设备分组"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="生产日期" prop="productDate">
        <el-date-picker clearable
          v-model="queryParams.productDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择生产日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="到期时间" prop="expiryDate">
          <el-date-picker clearable
            v-model="queryParams.expiryDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择到期时间">
          </el-date-picker>
        </el-form-item> -->
      <el-form-item label="是否点检" prop="pointCheck">
        <el-input
          v-model="queryParams.pointCheck"
          placeholder="请输入是否点检"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-full-screen"
          size="mini"
          :disabled="single"
          @click="qrGenerate"
        >生成二维码</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-full-screen"
          size="mini"
          :disabled="multiple"
          @click="qrGenerateList"
          v-hasPermi="['fire:firefighting:qrcode']"
        >批量生成二维码</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-printer"
          size="mini"
          :disabled="multiple"
          @click="qrPrint"
        >打印二维码</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['fire:firefighting:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['fire:firefighting:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['fire:firefighting:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['fire:firefighting:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fire:firefighting:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- <el-table v-loading="loading" :data="firefightingList" @selection-change="handleSelectionChange" :style="tabStyle"> -->
      
    <el-table v-loading="loading" :data="firefightingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" align="center" prop="index">
        <template slot-scope="scope">
          <span>{{(queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="消防设施ID" align="center" prop="fireId" /> -->
      <el-table-column label="设备名称" align="center" prop="deviceName" />
      <el-table-column label="设备型号" align="center" prop="deviceModel" />
      <el-table-column label="放置位置" align="center" prop="locate" />
      <el-table-column label="生产日期" align="center" prop="productDate" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="安装时间" align="center" prop="installTime" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.installTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到期时间" align="center" prop="expiryDate" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expiryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态是否有效" align="center" prop="status" />
      <el-table-column label="是否点检" align="center" prop="pointCheck" />
      <el-table-column label="设备图片" align="center" prop="image" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.image" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="设备分组" align="center" prop="deviceGroup" />
      <el-table-column label="二维码内容" align="center" prop="qrContent" width="120" />
       <!-- <el-table-column label="灭火器ID" align="center" prop="killFireId" />
      <el-table-column label="消火栓ID" align="center" prop="fireHydrantId" />
      <el-table-column label="水泵房ID" align="center" prop="pumpRoomId" />
      <el-table-column label="阀组间ID" align="center" prop="groupRoomId" />-->
      <el-table-column label="最新检查时间" align="center" prop="checkRecords" width="100" />
     <!-- <el-table-column label="维护记录" align="center" prop="maintainRecords" />
      <el-table-column label="报警数据" align="center" prop="alarmData" /> -->
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-full-screen"
            @click="qrGenerate(scope.row)"
            v-hasPermi="['fire:firefighting:qrcode']"
          >生成二维码</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fire:firefighting:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fire:firefighting:remove']"
          >删除</el-button>
        </template>
      </el-table-column> -->
      <el-table-column label="二维码" align="center" prop="qrcode">
        <template slot-scope="scope">
          <div class="demo-image__preview">
            <el-image 
              style="width: 100%; height: 100%"
              :src="scope.row.qrcode" 
              :preview-src-list="srcList">
              <!-- @load="onImageLoad"> -->
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </div>
        </template>    
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改消防设施管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="form.deviceName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备型号" prop="deviceModel">
          <el-input v-model="form.deviceModel" placeholder="请输入设备型号" />
        </el-form-item>
        <el-form-item label="放置位置" prop="locate">
          <el-input v-model="form.locate" placeholder="请输入放置位置" />
        </el-form-item>
        <el-form-item label="生产日期" prop="productDate">
          <el-date-picker clearable
            v-model="form.productDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择生产日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="安装时间" prop="installTime">
          <el-date-picker clearable
            v-model="form.installTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择安装时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="到期时间" prop="expiryDate">
          <el-date-picker clearable
            v-model="form.expiryDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择到期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否点检" prop="pointCheck">
          <el-input v-model="form.pointCheck" placeholder="请输入是否点检" />
        </el-form-item>
        <el-form-item label="设备图片" prop="image">
          <image-upload v-model="form.image"/>
        </el-form-item>
        <el-form-item label="二维码内容" prop="qrContent">
          <el-input v-model="form.qrContent" placeholder="请输入二维码内容" />
        </el-form-item>
        <!-- <el-form-item label="灭火器ID" prop="killFireId">
          <el-input v-model="form.killFireId" placeholder="请输入灭火器ID" />
        </el-form-item>
        <el-form-item label="消火栓ID" prop="fireHydrantId">
          <el-input v-model="form.fireHydrantId" placeholder="请输入消火栓ID" />
        </el-form-item>
        <el-form-item label="水泵房ID" prop="pumpRoomId">
          <el-input v-model="form.pumpRoomId" placeholder="请输入水泵房ID" />
        </el-form-item>
        <el-form-item label="阀组间ID" prop="groupRoomId">
          <el-input v-model="form.groupRoomId" placeholder="请输入阀组间ID" />
        </el-form-item>
        <el-form-item label="设备分组" prop="deviceGroup">
          <el-input v-model="form.deviceGroup" placeholder="请输入设备分组" />
        </el-form-item>
        <el-form-item label="检查记录" prop="checkRecords">
          <el-input v-model="form.checkRecords" placeholder="请输入检查记录" />
        </el-form-item> -->
        <el-form-item label="维护记录" prop="maintainRecords">
          <el-input v-model="form.maintainRecords" placeholder="请输入维护记录" />
        </el-form-item>
        <el-form-item label="报警数据" prop="alarmData">
          <el-input v-model="form.alarmData" placeholder="请输入报警数据" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
     <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的消防设备数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 打印对话框 -->
    <el-dialog center :title="title" :visible.sync="openPrint" width="960px" append-to-body>
      <section id="printall" style="zoom:140%"></section>
      <section ref="pform" id="printMe" style="margin-top: 50px;margin-bottom: 50px;">
        
          <!-- <div class="el-row" style="height: 2.4cm; width: 10cm;padding-top: 3mm"> -->
          <!-- 
            <div class="item-long" >
              <div class="el-col el-col-3 el-col-offset-0">
                <div class="narrow">{{firefightingList.qrcode}}</div>
              </div>
              <div class="item-text-long el-col el-col-16">
               
              </div>
            </div> -->
            <div class="narrow">{{firefightingList.qrcode}}</div>
            <div class="demo-image__preview">
              <el-image 
                style="width: 100%; height: 100%"
                :src="firefightingList.qrcode" 
                :preview-src-list="srcList"
                @load="onImageLoad">

              </el-image>
            </div>
        <!-- </div> -->
      </section>

    </el-dialog>

  </div>
</template>

<script>
import { listFirefighting, getFirefighting, delFirefighting, addFirefighting, updateFirefighting, qrImg, qrImgList} from "@/api/fire/firefighting";
import { getToken, setToken, removeToken } from '@/utils/auth'
import { addAlert , updateAlert , listAlert} from "@/api/alert/alert";
import print from 'print-js'

export default {
  name: "Firefighting",
  data() {
    return {
      url:[],
      srcList: [],
      index:"",
      qrcode:"",
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 消防设施管理表格数据
      firefightingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //打印
      openPrint:false,
      printLoading: true,
      printObj: {
        id: "printMe",
        popTitle: 'good print',
        extraCss: "https://cdn.bootcdn.net/ajax/libs/hover.css/2.3.1/css/hover-min.css",
        extraHead: '<meta http-equiv="Content-Language"content="zh-cn"/>',
        beforeOpenCallback (vue) {
          vue.printLoading = true
          console.log('打开之前')
        },
        openCallback (vue) {
          vue.printLoading = false
          console.log('执行了打印')
        },
        closeCallback (vue) {
          console.log('关闭了打印工具')
        }
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceName: null,
        deviceModel: null,
        locate: null,
        productDate: null,
        installTime: null,
        expiryDate: null,
        status: null,
        pointCheck: null,
        image: null,
        qrContent:null,
        killFireId: null,
        fireHydrantId: null,
        pumpRoomId: null,
        groupRoomId: null,
        deviceGroup: null,
        checkRecords: null,
        maintainRecords: null,
        alarmData: null,
        qrcode:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/fire/firefighting/importData"
      },
      width: '',
      alertform: {
        id: null,
        fireId: null,
        deviceName: null,
        deviceModel: null,
        locate: null,
        alertRecord: null,
        alertTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      },
      fireIDNumber: [],
      queryParams2: {
        pageNum: 1,
        pageSize: 10,
        fireId: null,
        deviceName: null,
        deviceModel: null,
        locate: null,
        alertRecord: null,
        alertTime: null,
        id:null
      },
    };
  },
  created() {
    //查询已有报警数据的fireId
    listAlert(this.queryParams2).then(r => {
      for(var j=0;j<r.total;j++){
        this.fireIDNumber.push(r.rows[j].fireId) ;
      }
      console.log(this.fireIDNumber)
    });
    this.getList(); 
  },
  // computed: {
  //   tabStyle() {
  //     return {
  //       width: this.width // 使用计算属性来绑定宽度
  //     };
  //   }
  // },
  methods: {
    /** 查询消防设施管理列表 */
    getList() {
      this.loading = true;
      listFirefighting(this.queryParams).then(response => {
        // 过期状态判断 更新数据库 4
        for(var i=0;i<response.total;i++){
          //更新报警记录数据库表
          var alert={};
          alert['fireId'] = response.rows[i].fireId;
          alert['deviceName'] = response.rows[i].deviceName;
          alert['deviceModel'] = response.rows[i].deviceModel;
          alert['locate'] = response.rows[i].locate;

          const staticDate = new Date(response.rows[i].expiryDate);
          // console.log(staticDate)
          // console.log("staticDate < new Date()"+staticDate < new Date())
          if(response.rows[i].expiryDate == null ){
            response.rows[i].status="无"
            updateFirefighting(response.rows[i]).then(); 

            alert['alertRecord'] = "设备异常(无过期时间)";
            console.log("alert"+JSON.stringify(alert))
            
            // const index = this.fireIDNumber.indexOf(alert['fireId'] );
            // console.log(index !== -1); // 输出：true
            // console.log(this.fireIDNumber.includes(response.rows[i].fireId))
            // //判断报警数据库表要更新还是新增
            // if(index !== -1){
            //   console.log("updateAlert")
            //   updateAlert(alert).then( );
            // }else{
            //   console.log("addAlert")
            //   addAlert(alert).then( );
            // }
            //判断报警数据库表要更新还是新增
            if(this.fireIDNumber.includes(response.rows[i].fireId)){
              // this.queryParams2.fireId = response.rows[i].fireId
              // listAlert(this.queryParams2).then(r=>{
              //   console.log('1 r.alertTime'+r.rows.alertTime)
              //   if(r.rows.alertTime > new Date() ){
              //     addAlert(alert).then(res => {
              //       console.log(res)
              //       console.log("addAlert")
              //     });
              //   }else if(r.rows.alertTime = new Date()){
                  
              //   }else{
              //     updateAlert(alert).then(res => {
              //       console.log(res)
              //       console.log("updateAlert")
              //     });
              //   }
              // })
              updateAlert(alert).then(res => {
                console.log(res)
                console.log("updateAlert")
              });
            }else{
              addAlert(alert).then(res => {
                console.log(res)
                console.log("addAlert")
              });
            }
            
          }else if(staticDate < new Date()){
            response.rows[i].status="否"
            updateFirefighting(response.rows[i]).then(); 

            alert['alertRecord'] = "设备已到期";
            console.log(JSON.stringify(alert))
            console.log(response.rows[i].fireId)
            console.log(this.fireIDNumber.includes(response.rows[i].fireId))
 
            //判断报警数据库表要更新还是新增
            if(this.fireIDNumber.includes(response.rows[i].fireId)){
              // this.queryParams2.fireId = response.rows[i].fireId
              // listAlert(this.queryParams2).then(r=>{
              //   console.log('2 r.alertTime'+r.rows.alertTime)
              //   if(r.rows.alertTime > new Date()){
              //     addAlert(alert).then(res => {
              //       console.log(res)
              //       console.log("addAlert")
              //     });
              //   }else if(r.rows.alertTime = new Date()){
                  
              //   }else{
              //     updateAlert(alert).then(res => {
              //       console.log(res)
              //       console.log("updateAlert")
              //     });
              //   }
              // })
              updateAlert(alert).then(res => {
                console.log(res)
                console.log("updateAlert")
              });
            }else{
              addAlert(alert).then(res => {
                console.log(res)
                console.log("addAlert")
              });
            }


          }else if(staticDate > new Date()){
            response.rows[i].status="是"
            updateFirefighting(response.rows[i]).then(); 
          }else {  }

          
        }

        this.firefightingList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
 
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        fireId: null,
        deviceName: null,
        deviceModel: null,
        locate: null,
        productDate: null,
        installTime: null,
        expiryDate: null,
        status: null,
        pointCheck: null,
        image: null,
        qrContent:null,
        killFireId: null,
        fireHydrantId: null,
        pumpRoomId: null,
        groupRoomId: null,
        deviceGroup: null,
        checkRecords: null,
        maintainRecords: null,
        alarmData: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        qrcode:null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      console.log(JSON.stringify(selection))
      this.ids = selection.map(item => item.fireId)
      console.log(this.ids)
      this.single = selection.length!==1
      this.multiple = !selection.length

      this.index = this.firefightingList.findIndex(item => item.fireId == this.ids);
    },
    qrGenerate(row){
      // const fireId = row.fireId || this.ids
      // console.log(fireId)
      // console.log(JSON.stringify( this.firefightingList))
      // console.log(JSON.stringify( row))

      qrImg(this.firefightingList[this.index].qrContent).then(response => {
        // console.log(response)
        this.url[0]="data:img/jpg;base64,"+ response;
        this.firefightingList[this.index].qrcode="data:img/jpg;base64,"+ response;
        row.qrcode = this.url[0]
        console.log(row.qrcode)
        console.log(this.firefightingList[this.index].qrcode)
        this.srcList= [ row.qrcode];
      });
    },
    qrPrint(row){
      console.log(JSON.stringify( row))
      this.printImages(this.srcList);
    },
    onImageLoad() {   // 触发图片预览
      // this.$el.querySelector('.el-image__inner').click();
    },
    qrGenerateList(row){
      console.log(JSON.stringify( row))
      // 传入 二维码内容 列表
      var qrContents=[]
      for(let i=0;i<this.ids.length;i++){
        qrContents[i] =this.firefightingList[i].qrContent
        // console.log(qrContents[i])
      }
      console.log(JSON.stringify( qrContents ))
      
      qrImgList(qrContents).then(response =>{
        for(let i=0;i<this.ids.length;i++){
          this.url[i] = "data:img/jpg;base64,"+ response[i];
          this.firefightingList[i].qrcode = this.url[i]
          this.srcList[i]= this.url[i]
          // this.srcList= [ row.qrcode];
        }
        // // row.qrcode = this.url
        //   console.log(JSON.stringify(this.url))

        // console.log(JSON.stringify(this.srcList))
      })
      // this.width ='1673px'
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加消防设施管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const fireId = row.fireId || this.ids
      getFirefighting(fireId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改消防设施管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.fireId != null) {
            updateFirefighting(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFirefighting(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const fireIds = row.fireId || this.ids;
      this.$modal.confirm('是否确认删除消防设施管理编号为"' + fireIds + '"的数据项？').then(function() {
        return delFirefighting(fireIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('fire/firefighting/export', {
        ...this.queryParams
      }, `firefighting_${new Date().getTime()}.xlsx`)
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "消防设备导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('fire/firefighting/importTemplate', {
      }, `消防设备信息_模板_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    printImages(images) {
      // 打开一个新窗口
      var printWindow = window.open('', '_blank');
      
      // 循环并写入图片
      images.forEach(function(image) {
        var imgTag = '<img src="' + image + '" />';
        // var imgTag = '<img src="' + image + '" />'+ '<span>这是'+ row.deviceName +' 的描述</span>';
        printWindow.document.write(imgTag);
      });
      
      // 关闭文档写入，并打印
      printWindow.document.close();
      setTimeout(() => {
        printWindow.print();
      }, 1000);
    },
  
  }
};
</script>

<style>
 @page {
    size: auto;
    margin: 0 10mm;
  }
  .procedure{
    word-wrap:break-word;
  }
  @media print {
    .el-table thead.is-group th {
      text-align: center
    }
    tbody {
      text-align: center;
      border: 0px solid #fff;
    }
    th {
      border: 0px solid #fff;
    }
    td {
      border: 0px solid #fff;
    }
    section{
      border: 0px solid #fff;
    }
    .contents {
      margin-left: 0px !important;
    }
    .mianer /deep/.el-main {
      padding: 0 !important;
    }
    img{
      display: block;
    }
  }
</style>