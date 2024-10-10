import request from '@/utils/request'

// 查询任务列表
export function listTask(query) {
  return request({
    url: '/system/task/list',
    method: 'get',
    params: query
  })
}

// 查询任务详细
export function getTask(taskId) {
  return request({
    url: '/system/task/' + taskId,
    method: 'get'
  })
}

// 新增任务
export function addTask(data) {
  return request({
    url: '/system/task',
    method: 'post',
    data: data
  })
}

// 修改任务
export function updateTask(data) {
  return request({
    url: '/system/task',
    method: 'put',
    data: data
  })
}

// 删除任务
export function delTask(taskId) {
  return request({
    url: '/system/task/' + taskId,
    method: 'delete'
  })
}

// 数据概览
export function overviewCharts(data) {
  return request({
    url: '/system/task/overviewCharts',
    method: 'post',
    data: data
  })
}

// 数据看板
export function dataDashboards() {
  return request({
    url: '/system/task/dataDashboards',
    method: 'post',
  })
}