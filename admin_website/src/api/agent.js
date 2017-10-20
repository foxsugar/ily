/**
 * Created by win7 on 2017/9/15.
 */
import fetch from '@/utils/fetch'

export function getList(page, size) {
  return fetch({
    url: '/agent/list',
    method: 'get',
    params: {
      page,
      size
    }
  })
}

export function charge(chargeForm) {
  return fetch({
    url: '/agent/charge',
    method: 'get',
    params: {
      chargeForm
    }
  })
}

export function agent(agentForm, method) {
  return fetch({
    url: '/agent',
    method: method,
    params: {
      agentForm
    }
  })
}

export function fetchList(query) {
  return fetch({
    url: '/agent/fetchdelegates',
    method: 'get',
    params: query
  })
}
