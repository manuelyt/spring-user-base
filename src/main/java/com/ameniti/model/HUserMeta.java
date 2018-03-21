package com.ameniti.model;

import javax.persistence.*;

@Entity
@Table(name = "h_users_meta")
public class HUserMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private long idhusers;
    @Column(name = "`key`")
    private String key;
    @Column(name = "`value`")
    private String value;

    public long getIdhusers() {
        return idhusers;
    }

    public void setIdhusers(long idhusers) {
        this.idhusers = idhusers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    // constructor

    public HUserMeta() {
        id = 0;
    }

    @Override
    public String toString() {
        return "HuserMeta [id=" + id + ", idhusers=" + idhusers + ", key=" + key + ", value=" + value + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HUserMeta other = (HUserMeta) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
