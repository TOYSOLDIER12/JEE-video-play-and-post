package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Comment;
import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements CommentManager {
    @Autowired
     CommentRepository commentRepository;
    @Override
    public Comment addComment(Comment comment) {
        if(comment.getCreator() == null || comment.getVideo() == null)
            return null;
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public boolean deleteComment(Comment comment) {
        Optional<Comment> optionalComment = commentRepository.findById(comment.getId());
        if(optionalComment.isEmpty())
            return false;
        commentRepository.delete(comment);
        return !commentRepository.existsById(comment.getId());
    }

    @Override
    public Comment getCommentById(long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if(optionalComment.isEmpty())
            return null;
        return optionalComment.get();
    }

    @Override
    public Comment updateComment(Comment comment) {
        Optional<Comment> optionalComment = commentRepository.findById(comment.getId());
        if(optionalComment.isEmpty())
            return null;
        Comment newComment = optionalComment.get();
        newComment.setContent(comment.getContent());
        newComment.setDate(comment.getDate());
        newComment.setVideo(comment.getVideo());
        newComment.setCreator(comment.getCreator());
        return commentRepository.save(newComment);
    }

    @Override
    public Creator findCreatorByComment(Comment comment) {
        return commentRepository.findById(comment.getId()).get().getCreator();
    }
}
