package com.kefiya.home.common;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime dateCreated;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateUpdated;
    protected Boolean deleted = false;
    @Version
    protected Long version = 0L;
    protected String apiCreated = "Postman";
    protected String apiUpdated = "Postman";
    protected Long createdByUser;
    protected Long updatedByUser;
    @Enumerated(EnumType.STRING)
    protected EntityStatusEnum entityStatus = EntityStatusEnum.ACTIVE;
}
