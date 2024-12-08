import validator from 'validator';

interface validation {
  error: string;
  params: object;
}

export function validateDateFormat(date: string, format: string): validation {
  return validator.isDate(date, {format}) ? null : {error: 'MESSAGE.ERROR.INVALID_DATE_FORMAT', params: {format}};
}
export function validateCurrentMonth(date: string): validation {
  const dateParts = date.split('/');
  const day = parseInt(dateParts[0], 10);
  const month = parseInt(dateParts[1], 10) - 1; // Months are 0-indexed in JavaScript
  const year = parseInt(dateParts[2], 10);

  const inputDate = new Date(year, month, day);
  const currentDate = new Date();
  return inputDate.getFullYear() === currentDate.getFullYear() && inputDate.getMonth() === currentDate.getMonth()
    ? null : {error: 'MESSAGE.ERROR.NOT_CURRENT_MONTH', params: {}};
}

export function validateFrenchDecimalNumber(num): validation {
  const regex = /^\d+(,\d+)?$/;
  return regex.test(num) ? null : {error: 'MESSAGE.ERROR.INVALID_NUMBER_FORMAT', params: {format: '#0,0#'}};
}

export default {
  isInvalidDateFormat: validateDateFormat,
  isNotFrenchDecimalNumber: validateFrenchDecimalNumber,
  isNotCurrentMonth: validateCurrentMonth
};
