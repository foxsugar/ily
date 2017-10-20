/**
 * Created by win7 on 2017/10/9.
 */
import fetch from '@/utils/fetch'

export function getList(page, size) {
  return fetch({
    url: '/product/list',
    method: 'get',
    params: {
      page,
      size
    }
  })
}

export function product(productForm, method) {
  return fetch({
    url: '/product',
    method: method,
    params: {
      productForm
    }
  })
}
