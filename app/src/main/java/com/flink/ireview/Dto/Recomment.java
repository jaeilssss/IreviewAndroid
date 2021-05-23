package com.flink.ireview.Dto;

public class Recomment {

    private Long id;

    private Long userId;

    private Long commentId;

    private Long boardId;

    private int likeCount;

    private String replyContent;

    private String createdAt;

    public Recomment(Long id, Long userId, Long commentId, Long boardId, int likeCount, String replyContent, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.commentId = commentId;
        this.boardId = boardId;
        this.likeCount = likeCount;
        this.replyContent = replyContent;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
