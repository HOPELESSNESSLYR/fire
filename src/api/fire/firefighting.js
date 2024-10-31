import request from '@/utils/request'

// 查询灭火器管理列表
export function listFirefighting(query) {
  return request({
    url: '/fire/firefighting/list',
    method: 'get',
    params: query
  })
}

// 查询灭火器管理详细
export function getFirefighting(fireId) {
  return request({
    url: '/fire/firefighting/' + fireId,
    method: 'get'
  })
}

// 新增灭火器管理
export function addFirefighting(data) {
  return request({
    url: '/fire/firefighting',
    method: 'post',
    data: data
  })
}

// 修改灭火器管理
export function updateFirefighting(data) {
  return request({
    url: '/fire/firefighting',
    method: 'put',
    data: data
  })
}

// 删除灭火器管理
export function delFirefighting(fireId) {
  return request({
    url: '/fire/firefighting/' + fireId,
    method: 'delete'
  })
}
