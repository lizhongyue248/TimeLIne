import request from './axios'
import type { components } from '@/types/v1'

export const wechatAuth = async (code: string) => {
  const response = await request.get<components['schemas']['CodeSession']>(`/wechat/auth/${code}`)
  return response.data
}
