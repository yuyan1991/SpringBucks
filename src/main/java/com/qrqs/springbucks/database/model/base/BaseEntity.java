package com.qrqs.springbucks.database.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
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
