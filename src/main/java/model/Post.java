package model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Post {
    private int id;
    private String content;
    private Date created;
    private Date updated;
    private List<Label> labels;
    private PostStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }
    public Post(int id, String content, Date created, Date updated, List<Label> labels, PostStatus status) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && Objects.equals(content, post.content) && Objects.equals(created, post.created) && Objects.equals(updated, post.updated) && Objects.equals(labels, post.labels) && Objects.equals(status, post.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, created, updated, labels, status);
    }



    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", labels=" + labels +
                ", status=" + status +
                '}';
    }
}
