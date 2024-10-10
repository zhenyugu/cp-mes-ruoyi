import request from '@/utils/request'

// 查询供应商库管理列表
export function listPurveyor(query) {
  return request({
    url: '/system/purveyor/list',
    method: 'get',
    params: query
  })
}

// 查询供应商库管理详细
export function getPurveyor(purveyorId) {
  return request({
    url: '/system/purveyor/' + purveyorId,
    method: 'get'
  })
}

// 新增供应商库管理
export function addPurveyor(data) {
  return request({
    url: '/system/purveyor',
    method: 'post',
    data: data
  })
}

// 修改供应商库管理
export function updatePurveyor(data) {
  return request({
    url: '/system/purveyor',
    method: 'put',
    data: data
  })
}

// 删除供应商库管理
export function delPurveyor(purveyorId) {
  return request({
    url: '/system/purveyor/' + purveyorId,
    method: 'delete'
  })
}
