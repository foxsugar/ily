/**
 * Created by win7 on 2017/10/18.
 */
import fetch from '@/utils/fetch'

export function getList(page, size) {
  return fetch({
    url: '/order/list',
    method: 'get',
    params: {
      page,
      size
    }
  })
}


