import request from '@/utils/request'

// 查询流程管理列表
export function listProcess(query) {
  return request({
    url: '/system/process/list',
    method: 'get',
    params: query
  })
}

// 查询流程管理详细
export function getProcess(processId) {
  return request({
    url: '/system/process/' + processId,
    method: 'get'
  })
}

// 新增流程管理
export function addProcess(data) {
  return request({
    url: '/system/process',
    method: 'post',
    data: data
  })
}

// 修改流程管理
export function updateProcess(data) {
  return request({
    url: '/system/process',
    method: 'put',
    data: data
  })
}

// 删除流程管理
export function delProcess(processId) {
  return request({
    url: '/system/process/' + processId,
    method: 'delete'
  })
}
