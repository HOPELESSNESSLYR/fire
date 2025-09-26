import request from '@/utils/request'

// 查询消防设施管理列表
export function listFirefighting(query) {
  return request({
    url: '/fire/firefighting/list',
    method: 'get',
    params: query
  })
}

// 查询消防设施管理详细
export function getFirefighting(fireId) {
  return request({
    url: '/fire/firefighting/' + fireId,
    method: 'get'
  })
}

// 新增消防设施管理
export function addFirefighting(data) {
  return request({
    url: '/fire/firefighting',
    method: 'post',
    data: data
  })
}

// 修改消防设施管理
export function updateFirefighting(data) {
  return request({
    url: '/fire/firefighting',
    method: 'put',
    data: data
  })
}

// 删除消防设施管理
export function delFirefighting(fireId) {
  return request({
    url: '/fire/firefighting/' + fireId,
    method: 'delete'
  })
}

export function qrImg(data) {
  return request({
    url: '/fire/firefighting/code/toStream',
    method: 'post',
    data: data
  })
}

export function qrImgList(data) {
  return request({
    url: '/fire/firefighting/code/list',
    method: 'post',
    data: data
  })
}
// 查询消防设施管理列表2
export function listFirefighting2(query) {
  return request({
    url: '/fire/firefighting/list2',
    method: 'get',
    params: query,
    // 提高该接口超时时间，缓解大数据量查询导致的前端超时
    timeout: 30000
  })
}

// 月度汇总
export function monthlySummary(query) {
  // 调用后端月度汇总接口，query 需包含 startDate/endDate（yyyy-MM-dd）
  return request({
    url: '/fire/firefighting/summary/monthly',
    method: 'get',
    params: query
  })
}
