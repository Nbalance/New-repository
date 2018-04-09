import axios from 'axios'
import config from '../config'

export default {
  request (method, uri, data = null, contentType = config.contentApplicationJson) {
    if (!method) {
      console.error('API function call requires method argument')
      return
    }

    if (!uri) {
      console.error('API function call requires uri argument')
      return
    }

    var url = config.serverURI + uri

    var instance = axios.create({
      // baseURL: 'http://101.37.84.120:8090',
      baseURL: '',
      timeout: 60000,
      withCredentials: true,
      headers: {
        'Content-Type': contentType
      }
    })

    instance.interceptors.response.use(function (response) {
      // 对响应数据做些事
      if (response.data.code === 401) {
        console.log('session timeout')
        window.router.push('../login')
        window.alert(response.data.message)
      } else if (response.data.code === 404) {
        window.router.push('notfound')
      } else {
        response.data.code === 401
        return response
      }
    }, function (error) {
      // 请求错误时做些事
      return Promise.reject(error)
    })

    console.log('request : ' + method + '  url: ' + url + 'data: ' + data)
    return instance({ method, url, data })
  },
  hasPermission (permission) {
    var permissions
    // console.log(window.gtstore)
    // console.log(permissions)
    if (window.gtstore === undefined || window.gtstore.state === null) {
      permissions = window.sessionStorage.getItem('SET_USER')
    } else {
      if (window.gtstore.state.user === null) {
        permissions = window.sessionStorage.getItem('SET_USER')
      } else {
        permissions = JSON.stringify(window.gtstore.state.user)
      }
    }
    var hasPermission = false
    // console.log(permissions)
    // console.log(permissions.indexOf(permission))
    if (permissions.indexOf(permission) !== -1) {
      hasPermission = true
    }
    return hasPermission
  },
  checkCellphonenumber (value) {
    return /^1[0-9]{10}$/.test(value)
  },
  checkPhoneNumber (value) {
    return /^0[0-9]{2,3}-?[0-9]{7,8}$/.test(value)
  },
  checkDateValue (value) {
    return /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/.test(value)
  },
  checkVersionValue (value) {
    return /^\d{1,2}(\.\d{1,2}){2,3}$/.test(value)
  }

}
