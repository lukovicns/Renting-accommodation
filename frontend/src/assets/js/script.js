export const datepicker = () => {
  let startDate = new Date();
  startDate.setDate(new Date().getDate() + 1);
  $('[data-toggle="datepicker"]').datepicker({
    autoPick: true,
    format: 'yyyy-MM-dd',
    startDate: startDate
  });
}
