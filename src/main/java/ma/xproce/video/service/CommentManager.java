package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Comment;
import ma.xproce.video.dao.entity.Creator;

import java.util.List;

public interface CommentManager {
    public Comment addComment(Comment comment);
    public List<Comment> getAllComments();
    public boolean deleteComment(Comment comment);
    public Comment getCommentById(long id);
    public Comment updateComment(Comment comment);
    public Creator findCreatorByComment(Comment comment);
}
