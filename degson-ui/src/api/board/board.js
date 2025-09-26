import request from '@/utils/request'

// 查询制作信息牌列表
export function listBoard(query) {
  return request({
    url: '/board/board/list',
    method: 'get',
    params: query
  })
}

// 查询制作信息牌详细
export function getBoard(id) {
  return request({
    url: '/board/board/' + id,
    method: 'get'
  })
}

// 新增制作信息牌
export function addBoard(data) {
  return request({
    url: '/board/board',
    method: 'post',
    data: data
  })
}

// 修改制作信息牌
export function updateBoard(data) {
  return request({
    url: '/board/board',
    method: 'put',
    data: data
  })
}

// 删除制作信息牌
export function delBoard(id) {
  return request({
    url: '/board/board/' + id,
    method: 'delete'
  })
}
