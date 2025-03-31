import request from '@/utils/request'

// 查询预警报警列表
export function listAlert(query) {
  return request({
    url: '/alert/alert/list',
    method: 'get',
    params: query
  })
}

// 查询预警报警详细
export function getAlert(id) {
  return request({
    url: '/alert/alert/' + id,
    method: 'get'
  })
}

// 新增预警报警
export function addAlert(data) {
  return request({
    url: '/alert/alert',
    method: 'post',
    data: data,
    headers: {skipRepeatSubmit: true}
  })
}

// 修改预警报警
export function updateAlert(data) {
  return request({
    url: '/alert/alert',
    method: 'put',
    data: data,
    headers: {skipRepeatSubmit: true}
  })
}

// 删除预警报警
export function delAlert(id) {
  return request({
    url: '/alert/alert/' + id,
    method: 'delete'
  })
}
