export class Reservation {
  constructor(
    public id: Number,
    public user: Number,
    public apartment: Number,
    public startDate: String,
    public endDate: Number,
    public price: Number,
    public status: string
  ) { }
}
