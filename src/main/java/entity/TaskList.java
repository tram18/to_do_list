package entity;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "task_lists.listAllTaskList", query = "SELECT t FROM TaskList t"),
        @NamedQuery(name = "task_lists.deleteTaskListById", query = "DELETE FROM TaskList t WHERE t.id = :id"),
        @NamedQuery(name = "task_lists.findByUser", query = "SELECT t FROM TaskList t WHERE t.user.id = :userId")

})
@Table(name = "task_lists")
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "list_name", nullable = false, length = 100)
    private String listName;

    @Column(name = "created_at", nullable = false, updatable = true)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, updatable = true)
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TaskItem> taskItems;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<TaskItem> getTaskItems() {
        return taskItems;
    }

    public void setTaskItems(List<TaskItem> taskItems) {
        this.taskItems = taskItems;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "id=" + id +
                ", user=" + user +
                ", listName='" + listName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
