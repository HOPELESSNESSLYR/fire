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
      <!-- <el-form-item label="生产日期" prop="productDate">
        <el-date-picker clearable
          v-model="queryParams.productDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择生产日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="安装时间" prop="installTime">
        <el-date-picker clearable
          v-model="queryParams.installTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择安装时间">
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
      <!-- <el-form-item label="灭火器ID" prop="killFireId">
        <el-input
          v-model="queryParams.killFireId"
          placeholder="请输入灭火器ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="消火栓ID" prop="fireHydrantId">
        <el-input
          v-model="queryParams.fireHydrantId"
          placeholder="请输入消火栓ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="水泵房ID" prop="pumpRoomId">
        <el-input
          v-model="queryParams.pumpRoomId"
          placeholder="请输入水泵房ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="阀组间ID" prop="groupRoomId">
        <el-input
          v-model="queryParams.groupRoomId"
          placeholder="请输入阀组间ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="设备分组" prop="group">
        <el-input
          v-model="queryParams.group"
          placeholder="请输入设备分组"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="检查记录" prop="checkRecords">
        <el-input
          v-model="queryParams.checkRecords"
          placeholder="请输入检查记录"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="维护记录" prop="maintainRecords">
        <el-input
          v-model="queryParams.maintainRecords"
          placeholder="请输入维护记录"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报警数据" prop="alarmData">
        <el-input
          v-model="queryParams.alarmData"
          placeholder="请输入报警数据"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-full-screen"
          size="mini"
          @click="qrGenerate"
        >二维码</el-button>
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

    <el-table v-loading="loading" :data="firefightingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="消防设施ID" align="center" prop="fireId" />
      <el-table-column label="设备名称" align="center" prop="deviceName" />
      <el-table-column label="设备型号" align="center" prop="deviceModel" />
      <el-table-column label="放置位置" align="center" prop="locate" />
      <el-table-column label="生产日期" align="center" prop="productDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="安装时间" align="center" prop="installTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.installTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态是否有效" align="center" prop="status" />
      <el-table-column label="是否点检" align="center" prop="pointCheck" />
      <el-table-column label="设备图片" align="center" prop="image" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.image" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="二维码内容" align="center" prop="qrContent" />
      <el-table-column label="灭火器ID" align="center" prop="killFireId" />
      <el-table-column label="消火栓ID" align="center" prop="fireHydrantId" />
      <el-table-column label="水泵房ID" align="center" prop="pumpRoomId" />
      <el-table-column label="阀组间ID" align="center" prop="groupRoomId" />
      <el-table-column label="设备分组" align="center" prop="group" />
      <el-table-column label="检查记录" align="center" prop="checkRecords" />
      <el-table-column label="维护记录" align="center" prop="maintainRecords" />
      <el-table-column label="报警数据" align="center" prop="alarmData" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
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
      </el-table-column>
      <el-table-column label="二维码" align="center" prop="qrcode">
        <template slot-scope="scope">
          <div class="demo-image__preview">
            <el-image 
              style="width: 100%; height: 100%"
              :src="url" 
              :preview-src-list="srcList">
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
        <el-form-item label="是否点检" prop="pointCheck">
          <el-input v-model="form.pointCheck" placeholder="请输入是否点检" />
        </el-form-item>
        <el-form-item label="设备图片" prop="image">
          <image-upload v-model="form.image"/>
        </el-form-item>
        <el-form-item label="二维码内容" prop="qrContent">
          <el-input v-model="form.qrContent" placeholder="请输入二维码内容" />
        </el-form-item>
        <el-form-item label="灭火器ID" prop="killFireId">
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
        <el-form-item label="设备分组" prop="group">
          <el-input v-model="form.group" placeholder="请输入设备分组" />
        </el-form-item>
        <el-form-item label="检查记录" prop="checkRecords">
          <el-input v-model="form.checkRecords" placeholder="请输入检查记录" />
        </el-form-item>
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
  </div>
</template>

<script>
import { listFirefighting, getFirefighting, delFirefighting, addFirefighting, updateFirefighting, qrImg} from "@/api/fire/firefighting";

export default {
  name: "Firefighting",
  data() {
    return {
      url:'',
      srcList: [],
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
      showSearch: true,
      // 总条数
      total: 0,
      // 消防设施管理表格数据
      firefightingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceName: null,
        deviceModel: null,
        locate: null,
        productDate: null,
        installTime: null,
        status: null,
        pointCheck: null,
        image: null,
        qrContent:null,
        killFireId: null,
        fireHydrantId: null,
        pumpRoomId: null,
        groupRoomId: null,
        group: null,
        checkRecords: null,
        maintainRecords: null,
        alarmData: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询消防设施管理列表 */
    getList() {
      this.loading = true;
      listFirefighting(this.queryParams).then(response => {
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
        status: null,
        pointCheck: null,
        image: null,
        qrContent:null,
        killFireId: null,
        fireHydrantId: null,
        pumpRoomId: null,
        groupRoomId: null,
        group: null,
        checkRecords: null,
        maintainRecords: null,
        alarmData: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    qrGenerate(){
      qrImg(this.firefightingList[0].qrContent).then(response => {
        this.url = "data:img/jpg;base64," + response;
        this.srcList= ["data:img/jpg;base64," + response];
      });
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
    }
  }
};
</script>
