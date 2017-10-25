export class User{

  userId:number;
  nickname: string;
  email:string;
  isAdmin: boolean;
  isBanned:boolean;


  constructor(userId: number, nickname: string, email: string, isAdmin: boolean, isBanned: boolean) {
    this.userId = userId;
    this.nickname = nickname;
    this.email = email;
    this.isAdmin = isAdmin;
    this.isBanned = isBanned;
  }
}
