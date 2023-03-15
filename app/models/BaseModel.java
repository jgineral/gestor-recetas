package models;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
public class BaseModel extends Model {
    @Id
    private Long id;
    @WhenCreated
    private Timestamp whenCreated;
    @WhenModified
    private Timestamp whenModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Timestamp whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Timestamp getWhenModified() {
        return whenModified;
    }

    public void setWhenModified(Timestamp whenModified) {
        this.whenModified = whenModified;
    }
}
