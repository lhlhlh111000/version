package com.ping.web.version.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "rn_version")
public class RNVersionDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "version_id")
    private long versionId;

    private int version;

    private String url;

    @Column(length = 64)
    private String sha1;

    private int type; // 0 默认，全量；1 增量

    private boolean enable;

    @Column(length = 32)
    private String size;

    @Column(name = "description")
    private String desc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
}