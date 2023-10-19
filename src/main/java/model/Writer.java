package model;

import java.util.List;
import java.util.Objects;

public class Writer {
    private int id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private PostStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Writer(int id, String firstName, String lastName, List<Post> posts, PostStatus status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts=" + posts +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return id == writer.id && Objects.equals(firstName, writer.firstName) && Objects.equals(lastName, writer.lastName) && Objects.equals(posts, writer.posts) && status == writer.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, posts, status);
    }
}

