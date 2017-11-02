import fetch from '@/utils/fetch'


export function getList(page, size) {
  return fetch({
    url: 'agent/fetchself',
    method: 'get',
    params: {
      page,
      size
    }
  })
}
