/**
 * Created by win7 on 2017/9/22.
 */
import fetch from '@/utils/fetch'

export function getList(page, size) {
  return fetch({
    url: '/player/list',
    method: 'get',
    params: {
      page,
      size
    }
  })
}

export function charge(chargeForm) {
  return fetch({
    url: '/player/charge',
    method: 'get',
    params: {
      chargeForm
    }
  })
}

export function fetchList(query) {
  return fetch({
    url: '/player/fetchplayers',
    method: 'get',
    params: query
  })
}

