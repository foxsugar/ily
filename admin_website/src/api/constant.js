/**
 * Created by win7 on 2017/9/22.
 */
import fetch from '@/utils/fetch'

export function constant(method, constantForm) {
  return fetch({
    url: '/constant',
    method: method,
    params: {
      constantForm
    }
  })
}

export function update(constantForm) {
  return fetch({
    url: '/constant/update',
    method: 'get',
    params: {
      constantForm
    }
  })
}


