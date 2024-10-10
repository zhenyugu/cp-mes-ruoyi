import request from '@/utils/request'

// 查询报工列表
export function listJobBooking(query) {
  return request({
    url: '/system/jobBooking/list',
    method: 'get',
    params: query
  })
}

// 查询不良品项明细
export function defectDetails(query) {
  return request({
    url: '/system/jobBooking/defectDetails',
    method: 'get',
    params: query
  })
}

// 查询报工详细
export function getJobBooking(jobBookingId) {
  return request({
    url: '/system/jobBooking/' + jobBookingId,
    method: 'get'
  })
}

// 新增报工
export function addJobBooking(data) {
  return request({
    url: '/system/jobBooking',
    method: 'post',
    data: data
  })
}

// 修改报工
export function updateJobBooking(data) {
  return request({
    url: '/system/jobBooking',
    method: 'put',
    data: data
  })
}

// 删除报工
export function delJobBooking(jobBookingId) {
  return request({
    url: '/system/jobBooking/' + jobBookingId,
    method: 'delete'
  })
}
