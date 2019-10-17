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
@Table(name = "app")
public class AppDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 32)
    private int platform;

    @Column(length = 32, name = "package")
    private String packageName;

    @Column(length = 32)
    private String name;

    @Column(name = "remarks")
    private String desc;

    private String icon;

    @Column(nullable = false)
    private String token;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "app_join_version",
        joinColumns = @JoinColumn(name = "app_id"),
        inverseJoinColumns = @JoinColumn(name = "version_id"))
    private AppVersionDO latestVersion;
}
