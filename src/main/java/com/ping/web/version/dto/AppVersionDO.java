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
@Table(name = "app_version")
public class AppVersionDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "app_id")
    private long appId;

    private int build;

    @Column(length = 32, name = "build_name")
    private String buildName;

    private String url;

    @Column(length = 32)
    private String size;

    @Column(name = "update_tips")
    private String updateTips;

    @Column(name = "is_force")
    private boolean force;

    private boolean enable;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "version_join_rn",
            joinColumns = @JoinColumn(name = "version_id"),
            inverseJoinColumns = @JoinColumn(name = "rn_id"))
    private RNVersionDO latestRNVersion;
}
