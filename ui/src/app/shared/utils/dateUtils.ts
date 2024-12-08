import {YMDDate} from '../models/beans';

export function formatDateDMY(date: Date) {
  if (!date) {
    return null;
  }
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();

  return `${day}/${month}/${year}`;
}

export function getMaxDateAsCurrentMonth(): YMDDate {
  const today = getTodayDate();
  const lastDayOfCurrentMonthDate = new Date(today.year, today.month, 0);
  return {
    year: lastDayOfCurrentMonthDate.getFullYear(),
    month: lastDayOfCurrentMonthDate.getMonth() + 1,
    day: lastDayOfCurrentMonthDate.getDate()
  };
}
export function getMinDateAsCurrentMonth(): YMDDate {
  const today = getTodayDate();
  const firstDayOfCurrentMonthDate = new Date(today.year, today.month, 1);
  return {
    year: firstDayOfCurrentMonthDate.getFullYear(),
    month: firstDayOfCurrentMonthDate.getMonth(),
    day: firstDayOfCurrentMonthDate.getDate()
  };
}

export function getTodayDate(): YMDDate {
  const date = new Date();
  return {
    year: date.getFullYear(),
    month: date.getMonth() + 1,
    day: date.getDate()
  };
}

export function formatrDate(date: string) {
  const parts = date.split('/');
  const day = parseInt(parts[0], 10);
  const month = parseInt(parts[1], 10) - 1;
  const year = parseInt(parts[2], 10);
  return new Date(year, month, day);
}
export function   setCurrentMonthName(month: number) {
  switch (month) {
    case 1:
      return 'jan';
    case 2:
      return 'fév';
    case 3:
      return 'mar';
    case 4:
      return 'avr';
    case 5:
      return 'mai';
    case 6:
      return 'jui';
    case 7:
      return 'juil';
    case 8:
      return 'août';
    case 9:
      return 'sept';
    case 10:
      return 'oct';
    case 11:
      return 'nov';
    case 12:
      return 'déc';
  }
}
