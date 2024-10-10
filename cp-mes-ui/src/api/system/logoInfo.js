import request from '@/utils/request'

// 查询logo信息列表
export function listLogo(query) {
  return request({
    url: '/system/logo/list',
    method: 'get',
    params: query
  })
}

// 查询logo信息详细
export function getLogo(id) {
  return request({
    url: '/system/logo/' + id,
    method: 'get'
  })
}

// 新增logo信息
export function addLogo(data) {
  return request({
    url: '/system/logo',
    method: 'post',
    data: data
  })
}

// 删除logo信息
export function delLogo(id) {
  return request({
    url: '/system/logo/' + id,
    method: 'delete'
  })
}