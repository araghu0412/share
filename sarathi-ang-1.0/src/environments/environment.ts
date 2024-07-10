import packageInfo from '../../package.json'

export const environment = {
  production: false,
  ENVIRONMENT_NAME: 'DEVELOPMENT',
  ACTIVE_PROFILE: 'D',
  SECRET_URL: 'http://localhost:8800/',
  APP_VERSION: packageInfo.version,
  PUBLIC_KEY: 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg2nG9/0YdNe9Tg14ZOGlTt+tJWSnRiq4KVBLzeZS4P/SeuqsawzZzgoTloDlcRAbffPdo4srvZrHHuQvGQwLHF++wcVGkpWU3hXOb87srbj2sxw8ckLEZGVz7IAWCP8ZMoO6azbI/hPSjkXTrFmCnenMPxO6bdQo9O9GUZD6XzIhKpFhFVJ3eiuzUmUdiehn4fVe2qqgiUK5CQjJQPRUrHJRKI4YctuC7MPfD9mjFs4yNr1EbHIrUKqZzDNMDEt0+lAk7HXFB23i9NRmCj06jHR+npGHHA077OFeluiMZglNtqkLyW7PPM95cFRKBnFXIX1N4Xjgg2YCiNa3WYU6UwIDAQAB'
};