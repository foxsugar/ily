/**
 * Created by win7 on 2017/9/15.
 */
import fetch from '@/utils/fetch'

export function getList(page, size) {
  return fetch({
    url: 'agent/chargelist',
    method: 'get',
    params: {
      page,
      size
    }
  })
}

export function fetchList(query) {
  return fetch({
    url: '/agent/fetchlist',
    method: 'get',
    params: query
  })
}
