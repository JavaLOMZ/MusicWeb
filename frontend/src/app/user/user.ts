export class User{

  userId:number;
  nickname: string;
  password: string;
  email:string;
  isAdmin: boolean;
  isBanned:boolean;


  constructor(userId: number, nickname: string,password: string, email: string, isAdmin: boolean, isBanned: boolean) {
    this.userId = userId;
    this.nickname = nickname;
    this.password=password;
    this.email = email;
    this.isAdmin = isAdmin;
    this.isBanned = isBanned;
  }
}
