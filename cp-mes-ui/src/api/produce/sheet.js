import request from '@/utils/request'

// 查询工单列表
export function listSheet(query) {
  return request({
    url: '/system/sheet/list',
    method: 'get',
    params: query
  })
}

// 查询工单列表(不分页)
export function allListSheet(query) {
  return request({
    url: '/system/sheet/allList',
    method: 'get',
    params: query
  })
}

// 查询工单详细
export function getSheet(sheetId) {
  return request({
    url: '/system/sheet/' + sheetId,
    method: 'get'
  })
}

// 查询工单详细
export function getSheetByNumber(query) {
  return request({
    url: '/system/sheet/sheetNumber',
    method: 'get',
    params: query
  })
}

// 新增工单
export function addSheet(data) {
  return request({
    url: '/system/sheet',
    method: 'post',
    data: data
  })
}

// 修改工单
export function updateSheet(data) {
  return request({
    url: '/system/sheet',
    method: 'put',
    data: data
  })
}

// 删除工单
export function delSheet(sheetId) {
  return request({
    url: '/system/sheet/' + sheetId,
    method: 'delete'
  })
}
