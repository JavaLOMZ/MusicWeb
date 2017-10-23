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

export class Comment {
  commentId:number;
  commentText: string;


  constructor(commentId: number, commentText: string) {
    this.commentText = commentText;
    this.commentId = commentId;

  }
}
