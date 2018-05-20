export class Apartment {
  constructor(
    public id: Number,
    public name: String,
    public bedType: Number,
    public description: String,
    public accommodation: Number,
    public size: Number,
    public maxNumberOfGuests: Number,
    public numberOfRooms: Number,
    public images: ByteString,
    public imageList: Array<String>
  ) { }
}
