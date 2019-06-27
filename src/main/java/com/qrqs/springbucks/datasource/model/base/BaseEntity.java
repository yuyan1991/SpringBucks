package com.qrqs.springbucks.datasource.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createTime;

    @Column(nullable = false)
    @UpdateTimestamp
    private Timestamp updateTime;
}
