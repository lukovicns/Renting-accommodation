export const datepicker = () => {
  let startDate = new Date();
  startDate.setDate(new Date().getDate() + 1);
  $('[data-toggle="datepicker"]').datepicker({
    autoPick: true,
    format: 'dd/MM/yyyy',
    startDate: startDate
  });
}

export const getFormattedDate = () => {
  let date = new Date();
  date.setDate(new Date().getDate() + 1);
  let year = date.getFullYear();
  let month = (1 + date.getMonth()).toString();
  month = month.length > 1 ? month : '0' + month;
  let day = date.getDate().toString();
  day = day.length > 1 ? day : '0' + day;
  return day + '/' + month + '/' + year;
}
