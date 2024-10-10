import request from '@/utils/request'

// 查询工序列表
export function listProcedure(query) {
  return request({
    url: '/system/procedure/list',
    method: 'get',
    params: query
  })
}

// 查询工序详细
export function getProcedure(procedureId) {
  return request({
    url: '/system/procedure/' + procedureId,
    method: 'get'
  })
}

// 新增工序
export function addProcedure(data) {
  return request({
    url: '/system/procedure',
    method: 'post',
    data: data
  })
}

// 修改工序
export function updateProcedure(data) {
  return request({
    url: '/system/procedure',
    method: 'put',
    data: data
  })
}

// 删除工序
export function delProcedure(procedureId) {
  return request({
    url: '/system/procedure/' + procedureId,
    method: 'delete'
  })
}
