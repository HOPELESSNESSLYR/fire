import request from '@/utils/request'

// 查询上车乘客列表
export function listQrcode(query) {
  return request({
    url: '/qrcode/qrcode/list',
    method: 'get',
    params: query
  })
}

// 查询上车乘客详细
export function getQrcode(id) {
  return request({
    url: '/qrcode/qrcode/' + id,
    method: 'get'
  })
}

// 新增上车乘客
export function addQrcode(data) {
  return request({
    url: '/qrcode/qrcode',
    method: 'post',
    data: data
  })
}

// 修改上车乘客
export function updateQrcode(data) {
  return request({
    url: '/qrcode/qrcode',
    method: 'put',
    data: data
  })
}

// 删除上车乘客
export function delQrcode(id) {
  return request({
    url: '/qrcode/qrcode/' + id,
    method: 'delete'
  })
}
