class Comment {
  commentId:number;
  commentText: string;


  constructor(userId: number, nickname: string, email: string, isAdmin: boolean, isBanned: boolean) {
    this.commentText = this.commentText;
    this.commentId = this.commentId;

  }
}

export class User{

  userId:number;
  nickname: string;
  email:string;
  isAdmin: boolean;
  isBanned:boolean;
  comments: Comment[];


  constructor(userId: number, nickname: string, email: string, isAdmin: boolean, isBanned: boolean) {
    this.userId = userId;
    this.nickname = nickname;
    this.email = email;
    this.isAdmin = isAdmin;
    this.isBanned = isBanned;
  }


}
