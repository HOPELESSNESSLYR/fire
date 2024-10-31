<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="放置位置" prop="locate">
        <el-input
          v-model="queryParams.locate"
          placeholder="请输入放置位置"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产日期" prop="productDate">
        <el-date-picker clearable
          v-model="queryParams.productDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择生产日期">
        </el-date-picker>
      </el-form-item>
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
      <el-table-column label="放置位置" align="center" prop="locate" />
      <el-table-column label="生产日期" align="center" prop="productDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态是否有效" align="center" prop="status" />
      <el-table-column label="是否点检" align="center" prop="pointCheck" />
      <el-table-column label="设备图片" align="center" prop="image" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.image" :width="50" :height="50"/>
        </template>
      </el-table-column>
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
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改灭火器管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
        <el-form-item label="状态是否有效" prop="status">
          <el-input v-model="form.status" placeholder="请输入状态是否有效" />
        </el-form-item>
        <el-form-item label="是否点检" prop="pointCheck">
          <el-input v-model="form.pointCheck" placeholder="请输入是否点检" />
        </el-form-item>
        <el-form-item label="设备图片" prop="image">
          <image-upload v-model="form.image"/>
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
import { listFirefighting, getFirefighting, delFirefighting, addFirefighting, updateFirefighting } from "@/api/fire/firefighting";

export default {
  name: "Firefighting",
  data() {
    return {
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
      // 灭火器管理表格数据
      firefightingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        locate: null,
        productDate: null,
        status: null,
        pointCheck: null,
        image: null
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
    /** 查询灭火器管理列表 */
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
        locate: null,
        productDate: null,
        status: null,
        pointCheck: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        image: null
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
      this.ids = selection.map(item => item.fireId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加灭火器管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const fireId = row.fireId || this.ids
      getFirefighting(fireId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改灭火器管理";
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
      this.$modal.confirm('是否确认删除灭火器管理编号为"' + fireIds + '"的数据项？').then(function() {
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
