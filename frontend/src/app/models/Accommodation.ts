export class Accommodation {
  constructor(
    public id: Number,
    public name: String,
    public type: Number,
    public city: Number,
    public street: String,
    public description: String,
    public category: Number,
    public agent: Number,
    public images: ByteString,
    public imageList: Array<String>
  ) { }
}
