<template>
  <div class="app-container">
    <!-- 月份选择：切换月份时触发 fetchData 重新拉取该月汇总数据 -->
    <el-form :inline="true" size="small" label-width="80px">
      <el-form-item label="月份">
        <el-date-picker
          v-model="month"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择月份"
          @change="fetchData"
        />
      </el-form-item>
    </el-form>

    <!-- 统计卡片：按设备组展示本月总数/已点检/未点检/合格/不合格 -->
    <el-row :gutter="16">
      <el-col :span="6" v-for="item in displayData" :key="item.key">
        <el-card shadow="hover">
          <div slot="header">{{ item.title }}</div>
          <div>总数：{{ item.totalCount || 0 }}</div>
          <div>已点检：{{ item.inspectedCount || 0 }}</div>
          <div>未点检：{{ item.uninspectedCount || 0 }}</div>
          <div>合格：{{ item.qualifiedCount || 0 }}</div>
          <div>不合格：{{ item.unqualifiedCount || 0 }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
  
</template>

<script>
import { monthlySummary } from '@/api/fire/firefighting'

export default {
  name: 'FireMonthlySummary',
  data() {
    return {
      // 当前选择的月份（格式 yyyy-MM），默认当前月
      month: this.defaultMonth(),
      // 后端返回的原始汇总数据数组，每个元素按 deviceGroup 聚合
      raw: [],
    }
  },
  computed: {
    displayData() {
      // 将原始数组转成 Map，便于按设备组快速取值
      const map = {}
      this.raw.forEach(r => { map[r.deviceGroup] = r })
      // 固定展示四类设备的顺序与标题
      const groups = [
        { key: '消防栓', title: '本月消防栓' },
        { key: '灭火器', title: '本月灭火器' },
        { key: '水泵房', title: '本月水泵房' },
        { key: '阀组间', title: '本月阀组间' },
      ]
      // 若某类设备后端无返回，则以空对象兜底，避免渲染报错
      return groups.map(g => ({ key: g.key, title: g.title, ...(map[g.key] || {}) }))
    }
  },
  created() {
    // 页面加载完成后默认请求当前月数据
    this.fetchData()
  },
  methods: {
    // 计算默认月份：当前系统时间所在的年-月
    defaultMonth() {
      const d = new Date()
      const m = String(d.getMonth() + 1).padStart(2, '0')
      return `${d.getFullYear()}-${m}`
    },
    // 根据所选月份计算该月起止日期，并调用后端月度汇总接口
    async fetchData() {
      const [y, m] = this.month.split('-')
      const startDate = `${y}-${m}-01`
      const endDate = new Date(parseInt(y), parseInt(m), 0)
      const end = `${endDate.getFullYear()}-${String(endDate.getMonth()+1).padStart(2,'0')}-${String(endDate.getDate()).padStart(2,'0')}`
      const res = await monthlySummary({ startDate, endDate: end })
      // 正常返回 data 为数组：[{ deviceGroup, totalCount, inspectedCount, uninspectedCount, qualifiedCount, unqualifiedCount }]
      this.raw = res.data || []
    }
  }
}
</script>

<style scoped>
</style>


