package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "task_items.listAllTaskItem", query = "SELECT e FROM TaskItem e"),
        @NamedQuery(name = "task_items.deleteItemById", query = "DELETE FROM TaskItem t WHERE t.id = :id")
})
@Table(name = "task_items")
public class TaskItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "task_name", nullable = false, length = 255)
    private String taskName;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

    @Column(name = "updated_at", nullable = false, updatable = true)
    private Timestamp updatedAt;

    @Column(name = "created_at", nullable = false, updatable = true)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "task_list_id", nullable = false)
    private TaskList taskList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Timestamp getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createdAt = createAt;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
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
        return "TaskItem{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", isCompleted=" + isCompleted +
                ", createAt=" + createdAt +
                '}';
    }

}
