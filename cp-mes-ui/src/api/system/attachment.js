import request from '@/utils/request'

// 查询备件库管理列表
export function listAttachment(query) {
  return request({
    url: '/system/attachment/list',
    method: 'get',
    params: query
  })
}

// 查询备件库管理详细
export function getAttachment(attachmentId) {
  return request({
    url: '/system/attachment/' + attachmentId,
    method: 'get'
  })
}

// 新增备件库管理
export function addAttachment(data) {
  return request({
    url: '/system/attachment',
    method: 'post',
    data: data
  })
}

// 修改备件库管理
export function updateAttachment(data) {
  return request({
    url: '/system/attachment',
    method: 'put',
    data: data
  })
}

// 删除备件库管理
export function delAttachment(attachmentId) {
  return request({
    url: '/system/attachment/' + attachmentId,
    method: 'delete'
  })
}