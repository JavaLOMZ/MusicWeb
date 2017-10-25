export class CommentOur {
  commentId:number;
  commentText: string;
  songId:number;
  userId:number;


  constructor(commentId: number, commentText: string, songId: number, userId:number) {
    this.commentId = commentId;
    this.commentText = commentText;
    this.songId=songId;
    this.userId=userId;
  }
}
