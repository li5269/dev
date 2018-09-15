/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xnx.Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(catalog = "blogdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Blog.findAll", query = "SELECT b FROM Blog b")
    , @NamedQuery(name = "Blog.findByBlogNo", query = "SELECT b FROM Blog b WHERE b.blogNo = :blogNo")
    , @NamedQuery(name = "Blog.findByCreatedTime", query = "SELECT b FROM Blog b WHERE b.createdTime = :createdTime")
    , @NamedQuery(name = "Blog.findByLabel", query = "SELECT b FROM Blog b WHERE b.label = :label")
    , @NamedQuery(name = "Blog.findByImage", query = "SELECT b FROM Blog b WHERE b.image = :image")
    , @NamedQuery(name = "Blog.findByContent", query = "SELECT b FROM Blog b WHERE b.content = :content")
    , @NamedQuery(name = "Blog.findByTitle", query = "SELECT b FROM Blog b WHERE b.title = :title")
    , @NamedQuery(name = "Blog.findByCollectedNum", query = "SELECT b FROM Blog b WHERE b.collectedNum = :collectedNum")
    , @NamedQuery(name = "Blog.findByCateAndLabel", query = "SELECT b From Blog b WHERE b.categoryCategoryId = :categoryCategoryId and b.label = :label")})
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer blogNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Created_Time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String label;
    @Size(max = 45)
    @Column(length = 45)
    private String image;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(nullable = false, length = 1000)
    private String content;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Collected_Num", nullable = false)
    private int collectedNum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "blogBlogNo")
    private Collection<Comment> commentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "blogBlogNo")
    private Collection<xnx.Entity.Collection> collectionCollection;
    @JoinColumn(name = "Category_CategoryId", referencedColumnName = "CategoryId", nullable = false)
    @ManyToOne(optional = false)
    private Category categoryCategoryId;
    @JoinColumn(name = "Author", referencedColumnName = "UserNo", nullable = false)
    @ManyToOne(optional = false)
    private User author;

    public Blog() {
    }

    public Blog(Integer blogNo) {
        this.blogNo = blogNo;
    }

    public Blog(Integer blogNo, Date createdTime, String label, String content, String title, int collectedNum) {
        this.blogNo = blogNo;
        this.createdTime = createdTime;
        this.label = label;
        this.content = content;
        this.title = title;
        this.collectedNum = collectedNum;
    }

    public Integer getBlogNo() {
        return blogNo;
    }

    public void setBlogNo(Integer blogNo) {
        this.blogNo = blogNo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollectedNum() {
        return collectedNum;
    }

    public void setCollectedNum(int collectedNum) {
        this.collectedNum = collectedNum;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    @XmlTransient
    public Collection<xnx.Entity.Collection> getCollectionCollection() {
        return collectionCollection;
    }

    public void setCollectionCollection(Collection<xnx.Entity.Collection> collectionCollection) {
        this.collectionCollection = collectionCollection;
    }

    public Category getCategoryCategoryId() {
        return categoryCategoryId;
    }

    public void setCategoryCategoryId(Category categoryCategoryId) {
        this.categoryCategoryId = categoryCategoryId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blogNo != null ? blogNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Blog)) {
            return false;
        }
        Blog other = (Blog) object;
        if ((this.blogNo == null && other.blogNo != null) || (this.blogNo != null && !this.blogNo.equals(other.blogNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Blog[ blogNo=" + blogNo + " ]";
    }

}