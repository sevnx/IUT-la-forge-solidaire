import { isBefore } from 'date-fns';

export function isDateBeforeToday(date: string | number | Date) {
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  today.setDate(today.getDate());
  return isBefore(new Date(date), today);
}
