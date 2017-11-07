export class User{

  private _userId:number;
  nickname: string;
  password: string;
  email:string;


  constructor(userId: number, nickname: string,password: string, email: string) {
    this._userId = userId;
    this.nickname = nickname;
    this.password=password;
    this.email = email;
  }


  get userId(): number {
    return this._userId;
  }
}
