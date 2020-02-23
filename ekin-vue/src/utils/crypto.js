import CryptoJS from 'crypto-js/crypto-js'

// 默认的 KEY 与 IV
const KEY = CryptoJS.enc.Utf8.parse('com.cartisan.www')
const IV = CryptoJS.enc.Utf8.parse('com.cartisan.www')

export function encrypt(content, keyStr, ivStr) {
  let key = KEY
  let iv = IV

  if (keyStr) {
    key = CryptoJS.enc.Utf8.parse(keyStr)
    iv = CryptoJS.enc.Utf8.parse(ivStr)
  }

  const srcs = CryptoJS.enc.Utf8.parse(content)
  var encrypted = CryptoJS.AES.encrypt(srcs, key, {
    iv: iv,
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.ZeroPadding
  })

  return CryptoJS.enc.Base64.stringify(encrypted.ciphertext)
}

export function decrypt(content, keyStr, ivStr) {
  let key = KEY
  let iv = IV

  if (keyStr) {
    key = CryptoJS.enc.Utf8.parse(keyStr)
    iv = CryptoJS.enc.Utf8.parse(ivStr)
  }

  const base64Content = CryptoJS.enc.Base64.parse(content)
  const srcs = CryptoJS.enc.Base64.stringify(base64Content)

  const decrypted = CryptoJS.AES.decrypt(srcs, key, {
    iv: iv,
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.ZeroPadding
  })

  return decrypted.toString(CryptoJS.enc.Utf8)
}
