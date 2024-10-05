package com.personal.blog_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "Posts")
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "post_title", nullable = false)
    private String title;

    @Column(name = "post_sub_title")
    private String subTitle;

    @Column(name = "post_content", nullable = false)
    private String content;

    @Column(name = "post_image")
    private String imageUrl;

    @Column(name = "post_createdAt")
    private Date createdAt;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
