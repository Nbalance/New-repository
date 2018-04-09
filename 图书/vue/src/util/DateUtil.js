export function formatDateByDefault (date) {
  if (date === undefined || date === null || date === '') {
    return ''
  }
  var getDate = new Date(date)
  var year = getDate.getFullYear()
  var month = getDate.getMonth() + 1
  month = month < 10 ? '0' + month : month
  var day = getDate.getDate()
  day = day < 10 ? '0' + day : day
  return year + '-' + month + '-' + day
}
export function formatDateOneToTwo (date, num) {
  if (date.length === 2) {
    return formatDateByDefault(date[parseInt(num)])
  } else {
    return ''
  }
}
