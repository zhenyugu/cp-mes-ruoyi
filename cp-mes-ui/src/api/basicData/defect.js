import request from '@/utils/request'

// 查询不良项列表
export function listDefect(query) {
  return request({
    url: '/system/defect/list',
    method: 'get',
    params: query
  })
}
// 查询不良项项分布
export function defectiveItems(query) {
  return request({
    url: '/system/defect/defectiveItems',
    method: 'get',
    params: query
  })
}
defectiveItems
// 查询不良项详细
export function getDefect(defectId) {
  return request({
    url: '/system/defect/' + defectId,
    method: 'get'
  })
}

// 新增不良项
export function addDefect(data) {
  return request({
    url: '/system/defect',
    method: 'post',
    data: data
  })
}

// 修改不良项
export function updateDefect(data) {
  return request({
    url: '/system/defect',
    method: 'put',
    data: data
  })
}

// 删除不良项
export function delDefect(defectId) {
  return request({
    url: '/system/defect/' + defectId,
    method: 'delete'
  })
}
