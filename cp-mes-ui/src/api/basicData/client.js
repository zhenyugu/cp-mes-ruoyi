import request from '@/utils/request'

// 查询客户信息列表
export function listClient(query) {
  return request({
    url: '/system/client/list',
    method: 'get',
    params: query
  })
}

// 查询客户信息详细
export function getClient(clientId) {
  return request({
    url: '/system/client/' + clientId,
    method: 'get'
  })
}

// 新增客户信息
export function addClient(data) {
  return request({
    url: '/system/client',
    method: 'post',
    data: data
  })
}

// 修改客户信息
export function updateClient(data) {
  return request({
    url: '/system/client',
    method: 'put',
    data: data
  })
}

// 删除客户信息
export function delClient(clientId) {
  return request({
    url: '/system/client/' + clientId,
    method: 'delete'
  })
}
