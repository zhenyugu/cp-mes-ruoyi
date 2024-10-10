import request from '@/utils/request'

// 查询工艺路线列表
export function listProcessRoute(query) {
  return request({
    url: '/system/processRoute/list',
    method: 'get',
    params: query
  })
}

// 查询工艺路线详细
export function getProcessRoute(processRouteId) {
  return request({
    url: '/system/processRoute/' + processRouteId,
    method: 'get'
  })
}

// 新增工艺路线
export function addProcessRoute(data) {
  return request({
    url: '/system/processRoute',
    method: 'post',
    data: data
  })
}

// 修改工艺路线
export function updateProcessRoute(data) {
  return request({
    url: '/system/processRoute',
    method: 'put',
    data: data
  })
}

// 删除工艺路线
export function delProcessRoute(processRouteId) {
  return request({
    url: '/system/processRoute/' + processRouteId,
    method: 'delete'
  })
}
