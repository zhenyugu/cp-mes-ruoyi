import request from '@/utils/request'

// 查询平台工单信息列表
export function listPlatformOrder(query) {
  return request({
    url: '/system/PlatformOrder/list',
    method: 'get',
    params: query
  })
}

// 查询平台工单信息详细
export function getPlatformOrder(platformOrderId) {
  return request({
    url: '/system/PlatformOrder/' + platformOrderId,
    method: 'get'
  })
}

// 新增平台工单信息
export function addPlatformOrder(data) {
  return request({
    url: '/system/PlatformOrder',
    method: 'post',
    data: data
  })
}

// 修改平台工单信息
export function updatePlatformOrder(data) {
  return request({
    url: '/system/PlatformOrder',
    method: 'put',
    data: data
  })
}

// 删除平台工单信息
export function delPlatformOrder(platformOrderId) {
  return request({
    url: '/system/PlatformOrder/' + platformOrderId,
    method: 'delete'
  })
}
